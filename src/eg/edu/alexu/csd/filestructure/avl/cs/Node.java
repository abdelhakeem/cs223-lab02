package eg.edu.alexu.csd.filestructure.avl.cs;

import eg.edu.alexu.csd.filestructure.avl.INode;

public class Node<T extends Comparable<T>> implements INode<T> {

	private Node<T> left;
	private Node<T> right;
	private T value;
	private int height = -1;

	public Node(T value) {
		this.value = value;
		this.height++;
	}

	@Override
	public INode<T> getLeftChild() {
		return left;
	}

	public void setLeftChild(INode<T> node) {
		this.left = (Node<T>) node;
	}

	@Override
	public INode<T> getRightChild() {
		return right;
	}

	public void setRightChild(INode<T> node) {
		this.right = (Node<T>) node;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	public int getHeight() {
		return height;
	}

	public void updateHeight() {
		// called after insertions or rotation
		height = Math.max(left.height, right.height) + 1;
	}
}
