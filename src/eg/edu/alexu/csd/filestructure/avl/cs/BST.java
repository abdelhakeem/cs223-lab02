package eg.edu.alexu.csd.filestructure.avl.cs;

import eg.edu.alexu.csd.filestructure.avl.IAVLTree;
import eg.edu.alexu.csd.filestructure.avl.INode;

public class BST<T extends Comparable<T>> implements IAVLTree<T> {

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
                insertBelow(node, rightChild, parentNode, 1);
            }
        } else {
            Node<T> leftChild = (Node<T>) parentNode.getLeftChild();
            if (leftChild == null) {
                parentNode.setLeftChild(node);
            } else {
                insertBelow(node, leftChild, parentNode, -1);
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
    	Node<T> parent = null;
        while (searcher != null) {
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
    				replacerParent = replacer;
    				replacer = replacerFinder;
    				replacerFinder = (Node<T>) replacerFinder.getRightChild();
    			}
    		}
    		replaceDeleted(searcher, parent, replacer, replacerParent);
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

    public void traverseTree(INode<T> toVisit) {
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
