package eg.edu.alexu.csd.filestructure.avl.cs;

import eg.edu.alexu.csd.filestructure.avl.INode;

public class Node<T extends Comparable<T>> implements INode<T> {

	private T value;
    private Node<T> left;
    private Node<T> right;
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

    /**
     * Called after insertions or rotation for
     * updating the height.
     */
    public void updateHeight() {
        int leftHeight = -1, rightHeight = -1;

        if (left != null) {
            leftHeight = left.height;
        }

        if (right != null) {
            rightHeight = right.height;
        }

        height = Math.max(leftHeight, rightHeight) + 1;
    }
}
