package eg.edu.alexu.csd.filestructure.avl.cs;

import eg.edu.alexu.csd.filestructure.avl.IAVLTree;
import eg.edu.alexu.csd.filestructure.avl.INode;

public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {

	private Node<T> root;
	private int size = 0;

	@Override
	public void insert(T key) {
		// TODO Abdelhakeem
	}

	@Override
	public boolean delete(T key) {
		// TODO H
		return false;
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
		return root.getHeight();
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
		return leftChild;
	}

	private Node<T> rotateLeft(Node<T> node) {
		Node<T> rightChild = (Node<T>) node.getLeftChild();
		node.setRightChild(rightChild.getLeftChild());
		rightChild.setLeftChild(node);
		node.updateHeight();
		rightChild.updateHeight();
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

	/**
	 * Getter for the size of the tree.
	 * @return
	 */
	public int getSize() {
		return size;
	}
}
