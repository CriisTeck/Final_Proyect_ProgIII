package structures.queue;

import structures.simpleList.Node;
import structures.simpleList.SimpleList;

import java.util.Comparator;
import java.util.Iterator;

public class Queue<E> implements Iterable<E>{
    private final SimpleList<E> list;

    public Queue(Comparator<E> comparator) {
        this.list = new SimpleList<>(comparator);
    }

    public E poll() {
        E aux = list.getInternalDataNode().getValue();
        list.remove(aux);
        return aux;
    }

    public void push(E data) {
        list.insert(data);

    }

    public E peek() {
        return list.getInternalDataNode().getValue();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean exist(E data){
        return list.exist(data);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> actual = list.getInternalDataNode();
            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public E next() {
                Node<E> aux = actual;
                actual = actual.getNext();
                return aux.getValue();
            }
        };
    }
}
