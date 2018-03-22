package eg.edu.alexu.csd.filestructure.avl.cs;

import java.util.Stack;

import eg.edu.alexu.csd.filestructure.avl.IAVLTree;
import eg.edu.alexu.csd.filestructure.avl.INode;

public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {

    private Node<T> root;
    private int size = 0;

    /**
     * Inserts the given node below the given parent node,
     * maintaining the AVL tree property.
     *
     * @param node inserted node
     * @param parent parent node
     * @param grandParent grandparent node, null if parent is root
     * @param parentSide relation of parent node to grandParent node.
     * Can be -1 or 1 depending on whether it is the left child or
     * the right child, respectively
     *
     * @return -1 or 1 indicating whether the node has been inserted
     * to the left or to the right, respectively
     */
    private int insertBelow(
            final INode<T> node,
            final INode<T> parent,
            final INode<T> grandParent,
            final int parentSide) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent should NOT be null");
        }

        if (node == null) {
            throw new IllegalArgumentException("Nothing to insert");
        }

        Node<T> parentNode = (Node<T>) parent;
        Node<T> grandParentNode = (Node<T>) grandParent;
        Node<T> newParent = parentNode;
        int side = -1;

        if (node.getValue().compareTo(parentNode.getValue()) > 0) {
            side = 1;
            Node<T> rightChild = (Node<T>) parentNode.getRightChild();
            if (rightChild == null) {
                parentNode.setRightChild(node);
            } else {
                int result = insertBelow(node, rightChild, parentNode, 1);
                int rightHeight = rightChild.getHeight();
                int leftHeight = -1;
                Node<T> leftChild = (Node<T>) parentNode.getLeftChild();

                if (leftChild != null) {
                    leftHeight = leftChild.getHeight();
                }

                if (rightHeight - leftHeight > 1) {
                    if (result == 1) {
                        newParent = rotateLeft(parentNode);
                    } else {
                        newParent = doubleRotationRightLeft(parentNode);
                    }
                }
            }
        } else {
            Node<T> leftChild = (Node<T>) parentNode.getLeftChild();
            if (leftChild == null) {
                parentNode.setLeftChild(node);
            } else {
                int result = insertBelow(node, leftChild, parentNode, -1);
                int leftHeight = leftChild.getHeight();
                int rightHeight = -1;
                Node<T> rightChild = (Node<T>) parentNode.getRightChild();

                if (rightChild != null) {
                    rightHeight = rightChild.getHeight();
                }

                if (leftHeight - rightHeight > 1) {
                    if (result == -1) {
                        newParent = rotateRight(parentNode);
                    } else {
                        newParent = doubleRotationLeftRight(parentNode);
                    }
                }
            }
        }

        if (grandParentNode == null) {
            this.root = newParent;
        } else {
            if (parentSide == -1) {
                grandParentNode.setLeftChild(newParent);
            } else {
                grandParentNode.setRightChild(newParent);
            }
        }

        parentNode.updateHeight();
        return side;
    }

    /**
     * Inserts a new node with the given key in the tree.
     *
     * @param key key of new node
     */
    @Override
    public final void insert(final T key) {
        if (root == null) {
            root = new Node<T>(key);
        } else {
            insertBelow(new Node<T>(key), root, null, 0);
        }

        size += 1;
    }

    @Override
    public boolean delete(T key) {
    	Node<T> searcher = root;
    	Stack<Node<T>> parentsStack = new Stack<Node<T>>();
    	Node<T> parent = null;
        while (searcher != null) {
        	parentsStack.push(searcher);
            if (searcher.getValue().compareTo(key) > 0) {
            	parent = searcher;
                searcher = (Node<T>) searcher.getLeftChild();
            } else if (searcher.getValue().compareTo(key) < 0) {
            	parent = searcher;
                searcher = (Node<T>) searcher.getRightChild();
            } else {
                break;
            }
        }
    	if (searcher == null) {
    		return false;
    	} else {
    		Node<T> replacer = null;
    		Node<T> replacerParent = searcher;
    		Node<T> replacerFinder = null;
    		int deletedLeftHeight = -1;
    		int deletedRightHeight = -1;
    		Node<T> deletedLeft = (Node<T>) searcher.getLeftChild();
    		Node<T> deletedRight = (Node<T>) searcher.getRightChild();
    		if (deletedLeft != null) {
    			deletedLeftHeight = deletedLeft.getHeight();
    		}
    		if (deletedRight != null) {
    			deletedRightHeight = deletedRight.getHeight();
    		}
    		if (deletedLeftHeight == -1) {
    			replacer = deletedRight;
    		} else if (deletedRightHeight == -1) {
    			replacer = deletedLeft;
    		} else if (deletedLeftHeight > deletedRightHeight) {
    			replacer = deletedRight;
    			if (replacer != null) {
    				replacerFinder = (Node<T>) replacer.getLeftChild();
    			}
    			while (replacerFinder != null) {
    				parentsStack.push(replacerParent);
    				replacerParent = replacer;
    				replacer = replacerFinder;
    				replacerFinder = (Node<T>) replacerFinder.getLeftChild();
    			}
    		} else {
    			replacer = deletedLeft;
    			if (replacer != null) {
    				replacerFinder = (Node<T>) replacer.getRightChild();
    			}
    			while (replacerFinder != null) {
    				parentsStack.push(replacerParent);
    				replacerParent = replacer;
    				replacer = replacerFinder;
    				replacerFinder = (Node<T>) replacerFinder.getRightChild();
    			}
    		}
    		replaceDeleted(searcher, parent, replacer, replacerParent);
    		fixAVL(parentsStack);
    		size--;
    		return true;
    	}
    }

    @Override
    public boolean search(T key) {
        Node<T> searcher = root;
        while (searcher != null) {
            if (searcher.getValue().compareTo(key) > 0) {
                searcher = (Node<T>) searcher.getLeftChild();
            } else if (searcher.getValue().compareTo(key) < 0) {
                searcher = (Node<T>) searcher.getRightChild();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public int height() {
    	if(root != null)
    		return root.getHeight()+1;
    	else
    		return 0;
    }

    @Override
    public INode<T> getTree() {
        return (INode<T>) this.root;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> leftChild = (Node<T>) node.getLeftChild();
        node.setLeftChild(leftChild.getRightChild());
        leftChild.setRightChild(node);
        node.updateHeight();
        leftChild.updateHeight();
        /*
        if (leftChild.getHeight() > root.getHeight()) {
        	root = leftChild;
        }*/
        return leftChild;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> rightChild = (Node<T>) node.getRightChild();
        node.setRightChild(rightChild.getLeftChild());
        rightChild.setLeftChild(node);
        node.updateHeight();
        rightChild.updateHeight();
        /*
        if (rightChild.getHeight() > root.getHeight()) {
        	root = rightChild;
        }
        */
        return rightChild;
    }

    private Node<T> doubleRotationLeftRight(Node<T> node) {
        Node<T> left = rotateLeft((Node<T>) node.getLeftChild());
        node.setLeftChild(left);
        return rotateRight(node);
    }

    private Node<T> doubleRotationRightLeft(Node<T> node) {
        Node<T> right = rotateRight((Node<T>) node.getRightChild());
        node.setRightChild(right);
        return rotateLeft(node);
    }

    private void replaceDeleted(INode<T> deleted, Node<T> parent, Node<T> replacer, Node<T> replacerParent) {
		if(replacer == null) {
			if (parent == null) {// Deleting one node in tree.
				root = null;
			} else {
				if (deleted == parent.getLeftChild()) {
					parent.setLeftChild(null);
				} else {
					parent.setRightChild(null);
				}
			}
		} else {
			deleted.setValue(replacer.getValue());
			if (replacer == replacerParent.getLeftChild()) {
				replacerParent.setLeftChild(null);
			} else {
				replacerParent.setRightChild(null);
			}
		}
    }

    /**
     * Fixes the AVL tree after a deletion/insertion operation
     * by backtracking through the trackStack updating heights
     * and rotating when required to maintinig the AVL property.
     * @param trackStack the stack that contains the route of
     * the operation from top to bottom.
     */
    private void fixAVL(Stack<Node<T>> trackStack) {
    	while (!trackStack.isEmpty()) {
    		Node<T> updated = trackStack.pop();
    		updated.updateHeight();
    		Node<T> leftChild = (Node<T>) updated.getLeftChild();
    		Node<T> rightChild = (Node<T>) updated.getRightChild();
    		int leftHeight = -1;
    		if (leftChild != null) {
    			leftHeight = leftChild.getHeight();
    		}
    		int rightHeight = -1;
    		if (rightChild != null) {
    			rightHeight = rightChild.getHeight();
    		}
    		Node<T> topNode = null;
    		if (leftHeight - rightHeight > 1) {
    			Node<T> leftLeftChild = (Node<T>) leftChild.getLeftChild();
    			Node<T> leftRightChild = (Node<T>) leftChild.getRightChild();
    			int leftLeftHeight = -1;
    			if (leftLeftChild != null) {
    				leftLeftHeight = leftLeftChild.getHeight();
    			}
    			int leftRightHeight = -1;
    			if (leftRightChild != null) {
    				leftRightHeight = leftRightChild.getHeight();
    			}
    			if (leftLeftHeight >= leftRightHeight) {
    				topNode = rotateRight(updated);
    			} else {
    				topNode = doubleRotationLeftRight(updated);
    			}
    		} else if (rightHeight - leftHeight > 1) {
    			Node<T> rightLeftChild = (Node<T>) rightChild.getLeftChild();
    			Node<T> rightRightChild = (Node<T>) rightChild.getRightChild();
    			int rightLeftHeight = -1;
    			if (rightLeftChild != null) {
    				rightLeftHeight = rightLeftChild.getHeight();
    			}
    			int rightRightHeight = -1;
    			if (rightRightChild != null) {
    				rightRightHeight = rightRightChild.getHeight();
    			}
    			if (rightRightHeight >= rightLeftHeight) {
    				topNode = rotateLeft(updated);
    			} else {
    				topNode = doubleRotationRightLeft(updated);
    			}
    		} else {
    			continue;
    		}
    		if (topNode != null) {
    			if (topNode.getHeight() > root.getHeight()) {
    				root = topNode;
    			}
    		}
    	}
    }

    private void traverseTree(INode<T> toVisit) {
    	if (toVisit.getLeftChild() != null) {
    		traverseTree(toVisit.getLeftChild());
    	}
    	System.out.println(toVisit.getValue());
    	if (toVisit.getRightChild() != null) {
    		traverseTree(toVisit.getRightChild());
    	}
    }

    /**
     * Getter for the size of the tree.
     * @return
     */
    public int getSize() {
        return size;
    }
}
