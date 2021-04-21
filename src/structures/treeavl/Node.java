package structures.treeavl;

public class Node<T> {
    private Node<T> right;
    private Node<T> left;
    private T data;

    public Node(T data) {
        right = null;
        left = null;
        this.data = data;
    }

    public Node<T> getRight() {
        return right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public T getData() {
        return data;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int calculateEquilibriumValue() {
        if(this.getRight() == null && this.getLeft() == null) return 0;
        if(this.getRight() == null) return -calculateHeight();
        if(this.getLeft() == null) return calculateHeight();
        else return (this.getRight().calculateHeight() - this.getLeft().calculateHeight());
    }

    public int calculateHeight() {
        return calculateHeight(this);
    }

    private int calculateHeight(Node<T> node) {
        int heightRight = (node.getRight() == null ? 0 : 1 + calculateHeight(node.getRight()));
        int heightLeft = (node.getLeft() == null ? 0 : 1 + calculateHeight(node.getLeft()));
        return Math.max(heightRight, heightLeft);
    }
}

