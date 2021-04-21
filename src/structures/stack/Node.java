package structures.stack;

public class Node<E> {
    private Node<E> top;
    private E value;

    public Node(E data) {
        top = null;
        this.value = data;
    }

    public Node<E> getTop() {
        return top;
    }

    public void setTop(Node<E> top) {
        this.top = top;
    }

    public E getValue() {
        return value;
    }
}
