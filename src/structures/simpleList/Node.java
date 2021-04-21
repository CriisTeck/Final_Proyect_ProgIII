package structures.simpleList;

public class Node<T> {
    private final T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
        next = null;
    }

    public T getValue(){
        return data;
    }

    public Node<T> getNext(){
        return next;
    }

    public void setNext(Node<T> next){
        this.next = next;
    }
}