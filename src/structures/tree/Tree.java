package structures.tree;

import java.util.Comparator;

public class Tree<T> {
    private Node<T> radix;
    private final Comparator<T> comparator;

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
        radix = null;
    }

    public void insert(T data) {
        radix = insertBinaryTree(data, radix);
    }

    private Node<T> insertBinaryTree(T data, Node<T> node) {
        if (node == null) {
            return new Node<>(data);
        } else {
            if (comparator.compare(node.getData(), data) > 0) {
                node.setLeft(insertBinaryTree(data, node.getLeft()));
            } else
                node.setRight(insertBinaryTree(data, node.getRight()));
            return node;
        }
    }

    public boolean exist(T data) {
        if (!isEmpty())
            return existBinaryTree(data, radix);
        else
            return false;
    }

    private boolean existBinaryTree(T data, Node<T> node) {
        if (comparator.compare(node.getData(),data) == 0) {
            return true;
        } else {
            boolean state = false;
            if (node.getLeft() != null) {
                state = existBinaryTree(data, node.getLeft());
            }
            if(state){
                return true;
            }
            if (node.getRight() != null) {
                state = existBinaryTree(data, node.getRight());
            }
            return state;
        }
    }

    public boolean isEmpty() {
        return radix == null;
    }

    public Node<T> getRadix() {
        return radix;
    }

    public Node<T> getLeft() {
        return radix.getLeft();
    }

    public Node<T> getRight() {
        return radix.getRight();
    }
}
