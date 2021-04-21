package structures.queuePriority;

import structures.queue.Queue;

import java.util.Arrays;
import java.util.Comparator;

public class QueuePriority<E> {
    private Queue<E>[] listQueue;

    public QueuePriority(int sizePriority, Comparator<E> comparator) {
        listQueue = new Queue[sizePriority];
        for (int i = 0; i < sizePriority; i++) {
            listQueue[i] = new Queue<E>(comparator);
        }
    }

    public void push(E data, int priority) throws Exception {
        if (priority >= listQueue.length)
            throw new Exception("Level priority not found");
        listQueue[priority].push(data);
    }

    public E poll() throws Exception {
        for (Queue<E> queue : listQueue) {
            if (!queue.isEmpty()) {
                return queue.poll();
            }
        }
        throw new Exception("Items not found.");
    }

    public E peek() throws Exception {
        for (Queue<E> queue : listQueue) {
            if (!queue.isEmpty()) {
                return queue.peek();
            }
        }
        throw new Exception("Items not found.");
    }

    public boolean isEmpty() {
        return Arrays.stream(listQueue).filter((Queue::isEmpty)).count() == listQueue.length;
    }

    public boolean exist(E data) {
        return Arrays.stream(listQueue).anyMatch(o1 -> o1.exist(data));
    }
}