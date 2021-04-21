package structures.simpleList;

import java.util.Comparator;

public class SimpleList<T> {
    private Node<T> head;
    private final Comparator<T> comparator;

    public SimpleList(Comparator<T> comparator) {
        head = null;
        this.comparator = comparator;
    }

    public void insert(T data) {
        Node<T> aux = head;
        boolean state = true;
        while (state) {
            if (head == null) {
                head = new Node<>(data);
                state = false;
            }
            if (aux != null) {
                if (aux.getNext() == null) {
                    aux.setNext(new Node<>(data));
                    state = false;
                } else aux = aux.getNext();
            }
        }
    }

    public boolean exist(T data) {
        Node<T> aux = head;
        while (true) {
            if (aux == null)
                return false;
            if (comparator.compare(aux.getValue(),data) == 0)
                return true;
            else if (aux.getNext() == null)
                return false;
            else
                aux = aux.getNext();
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void remove(T data) {
        Node<T> actual = head;
        Node<T> previous = head;
        boolean state = true;

        boolean isHead = true;
        if (!isEmpty()) {
            while (state && actual != null) {


                if (comparator.compare(actual.getValue(),data) == 0) {
                    if (actual == head) {
                        head = head.getNext();
                    } else {
                        previous.setNext(actual.getNext());
                        state = false;
                    }
                } else if (!isHead) { previous = previous.getNext();}
                actual = actual.getNext();
                isHead = false;
            }
        }
    }

    public String show() {
        Node<T> aux = head;
        StringBuilder message = new StringBuilder("[");
        while (true) {
            if (aux == null)
                return "[]";
            else
                message.append(aux.getValue()).append(", ");
            if (aux.getNext() == null)
                return (message.substring(0, message.length() - 2)) + "]";
            else aux = aux.getNext();
        }
    }

    public Node<T> getInternalDataNode(){
        return head;
    }
}
