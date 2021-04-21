package structures.doubleList;

import java.util.Comparator;

public class DoubleList<T> {
    private Node<T> head;
    private final Comparator<T> comparator;

    public DoubleList(Comparator<T> comparator) {
        this.comparator = comparator;
        head = null;
    }

    public void insert(T data) {
        Node<T> actual = head;
        boolean state = true;
        while (state) {
            if (head == null) {
                head = new Node<>(data);
                state = false;
            }
            if (actual != null) {
                if (actual.getNext() == null) {
                    actual.setNext(new Node<>(data));
                    actual.getNext().setPrevious(actual);
                    state = false;
                } else{
                    actual = actual.getNext();
                }
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

    public boolean isEmpty(){return head == null;}

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
                        actual.getNext().setPrevious(previous);
                        previous.setNext(actual.getNext());
                        state = false;
                    }
                } else if (!isHead) { previous = previous.getNext();}
                actual = actual.getNext();
                isHead = false;
            }
        }
    }

    public String show(){
        Node<T> aux = head;
        StringBuilder message = new StringBuilder("[");
        while (true) {
            if (aux == null)
                return "[]";
            else
                message.append(aux.getValue()).append(" ⮀ ");
            if (aux.getNext() == null)
                return (message.substring(0, message.length() - 3)) + "]";
            else aux = aux.getNext();
        }
    }

    public String showReverse() {
        Node<T> aux = head;
        Node<T> previous = head;
        StringBuilder message = new StringBuilder("[");
        while (true){
            if (aux == null) {
                return "[]";
            } else if (aux.getNext() == null){
                while(true){
                    message.append(aux.getValue()).append(" ⮀ ");
                    if(aux.getPrevious() == null )
                        return (message.substring(0, message.length() - 3)) + "]";
                    else
                        aux = aux.getPrevious();
                }
            } else
                aux = aux.getNext();
        }
    }

    public Node<T> getInternalDataNode(){
        return head;
    }

}
