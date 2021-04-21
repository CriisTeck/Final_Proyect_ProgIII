package structures.stack;

import java.util.Comparator;
import java.util.Iterator;

public class Stack<E> implements Iterable<E> {
    private Node<E> top;
    private Comparator<E> comparator;

    public Stack(Comparator<E> comparator) {
        top = null;
        this.comparator = comparator;
    }

    public void push(E data) {
        Node<E> aux = new Node<E>(data);
        aux.setTop(top);
        top = aux;
    }

    public E pop() {
        if (top != null) {
            Node<E> aux = top;
            top = top.getTop();
            return aux.getValue();
        } else return null;
    }

    public E peek(){
        return top != null ? top.getValue() : null;
    }

    public boolean exist(E data){
        Node<E> aux = top;
        while (aux != null) {
            if (comparator.compare(aux.getValue(),data) == 0)
                return true;
            aux = aux.getTop();
        }
        return false;
    }

    public E searchByData(E data){
        Node<E> aux = top;
        while (aux != null) {
            if (comparator.compare(aux.getValue(),data) == 0)
                return aux.getValue();
            aux = aux.getTop();
        }
        return null;
    }

    public boolean isEmpty() {
        return top == null;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> actual = top;
            @Override
            public boolean hasNext() {
                return actual.getTop() != null;
            }

            @Override
            public E next() {
                Node<E> aux = actual;
                actual = actual.getTop();
                return aux.getValue();
            }
        };
    }
}