package eg.edu.alexu.csd.filestructure.avl.cs;

import eg.edu.alexu.csd.filestructure.avl.INode;

public class Node<T extends Comparable<T>> implements INode<T>{

	private INode<T> left;
	private INode<T> right;
	private T value;
	private int height = 0;

	public Node(T value){
		this.value = value;
		this.height = 1;
	}

	@Override
	public INode<T> getLeftChild() {
		return left;
	}

	@Override
	public INode<T> getRightChild() {
		return right;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}
}
