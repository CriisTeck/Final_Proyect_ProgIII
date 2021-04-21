package structures.doubleList;

public class Node<T> {
    private final T data;
    private Node<T> next;
    private Node<T> previous;


    public Node(T data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    public T getValue() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }
}
