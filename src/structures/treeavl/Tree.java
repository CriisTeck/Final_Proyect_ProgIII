package structures.treeavl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Tree<T>{
    private Node<T> radix;
    private final Comparator<T> comparator;

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
        radix = null;
    }

    public void insert(T data) {
        radix = insert(data, radix);
    }

    public T searchData(T data) {
        return searchData(data, radix);
    }

    public boolean isEmpty() {
        return radix == null;
    }

    public int countNodesTree() {
        return countNodes(radix);
    }

    public boolean exist(T data) {
        if (!isEmpty()) return exist(data, radix);
        else return false;
    }

    private Node<T> insert(T data, Node<T> node) {
        if (node == null) {
            return new Node<>(data);
        } else {
            if (comparator.compare(node.getData(), data) > 0) {
                node.setLeft(insert(data, node.getLeft()));
            } else if (comparator.compare(node.getData(), data) < 0) {
                node.setRight(insert(data, node.getRight()));
            }
            node = verifyEquilibriumFactor(node);
            return node;
        }
    }

    private Node<T> verifyEquilibriumFactor(Node<T> node) {
        Node<T> aux = node;
        if(aux == null)
            return null;
        int fe = aux.calculateEquilibriumValue();
        while (fe > 1 || fe < -1) {
            aux = evaluateRotation(node);
            fe = aux.calculateEquilibriumValue();
        }
        return aux;
    }

    private Node<T> evaluateRotation(Node<T> node) {
        int n = node.calculateEquilibriumValue();
        int n1 = -100;
        if (n == 2) {
            if (node.getRight() != null)
                n1 = node.getRight().calculateEquilibriumValue();
            if (n1 >= 0) return rotationDD(node);
            if (n1 == -1) return rotationID(node);
        }
        if (n == -2) {
            n1 = node.getLeft().calculateEquilibriumValue();
            if (n1 <= 0) return rotationII(node);
            if (n1 == 1) return rotationDI(node);
        }
        return node;
    }

    private Node<T> rotationDI(Node<T> node) {
        Node<T> n = node, n1 = null, n2 = null;
        if (node.getLeft().calculateEquilibriumValue() > 1 || node.getLeft().calculateEquilibriumValue() < -1)
            n1 = node.getLeft();
        else if (node.getRight().calculateEquilibriumValue() > 1 || node.getRight().calculateEquilibriumValue() < -1)
            n1 = node.getRight();

        n2 = n1.getRight();

        n.setLeft(n2.getRight());
        n2.setRight(n);
        n1.setRight(n2.getLeft());
        n2.setLeft(n1);
        n = n2;
        return n;
    }

    private Node<T> rotationID(Node<T> node) {
        Node<T> n = node, n1 = null, n2 = null;
        if (node.getLeft().calculateEquilibriumValue() > 1 || node.getLeft().calculateEquilibriumValue() < -1)
            n1 = node.getLeft();
        else if (node.getRight().calculateEquilibriumValue() > 1 || node.getRight().calculateEquilibriumValue() < -1)
            n1 = node.getRight();

        if (n1 != null) {
            n2 = n1.getLeft();
            n.setRight(n2.getLeft());
            n2.setLeft(n);
            n1.setLeft(n2.getRight());
            n2.setRight(n1);
            n = n2;
        } else {
            n.setRight(null);
        }
        return n;

    }

    private Node<T> rotationDD(Node<T> node) {
        Node<T> n = node, n1 = node.getRight();
        n.setRight(n1.getLeft());
        n1.setLeft(n);
        n = n1;
        return n;
    }

    private Node<T> rotationII(Node<T> node) {
        Node<T> n = node, n1 = node.getLeft();
        n.setLeft(n1.getRight());
        n1.setRight(n);
        n = n1;
        return n;
    }

    private boolean exist(T data, Node<T> node) {
        if (comparator.compare(node.getData(), data) == 0) return true;
        else {
            boolean state = false;
            if (node.getLeft() != null) state = exist(data, node.getLeft());
            if (state) return true;
            if (node.getRight() != null) state = exist(data, node.getRight());
            return state;
        }
    }

    private T searchData(T data, Node<T> node) {
        if (node == null) return null;
        if (comparator.compare(node.getData(), data) == 0) return node.getData();
        else {
            T state = null;
            if (node.getLeft() != null) state = searchData(data, node.getLeft());
            if (state != null) return state;
            if (node.getRight() != null) state = searchData(data, node.getRight());
            return state;
        }

    }

    private int countNodes(Node<T> node) {
        int counter = 1;
        if (node.getLeft() == null && node.getRight() == null) return counter;
        if (node.getLeft() != null) counter += countNodes(node.getLeft());
        if (node.getRight() != null) counter += countNodes(node.getRight());
        return counter;
    }

    public void remove(T data) throws Exception {
        radix = removeNode(data, radix);
    }

    private Node<T> removeNode(T d, Node<T> n) throws Exception {
        if (n == null)
            throw new Exception("Nodo no Encontrado");
        else if ((comparator.compare(n.getData(), d) < 0)) {
            Node<T> right = removeNode(d, n.getRight());
            n.setRight(right);
        } else if (comparator.compare(n.getData(), d) > 0) {
            Node<T> left = removeNode(d, n.getLeft());
            n.setLeft(left);
        } else {
            Node<T> q = n;
            if (q.getLeft() == null) n = q.getRight();
            else if (q.getRight() == null) n = q.getLeft();
            else q = replace(q);
            q = null;
        }
        verifyEquilibriumFactor(n);
        return n;
    }

    private Node<T> replace(Node<T> q) {
        Node<T> a, p;
        p = q;
        a = q.getLeft();
        while (a.getRight() != null) {
            p = a;
            a = a.getRight();
        }
        q.setData(a.getData());
        if (p.equals(q)) {
            p.setLeft(a.getLeft());
        } else p.setRight(a.getRight());
        return a;
    }

    public ArrayList<T> getInorder() {
        return inorderRide(radix);
    }

    private ArrayList<T> inorderRide(Node<T> node) {
        ArrayList<T> inorderList = new ArrayList<T>();
        if (node == null)
            return inorderList;

        inorderList.addAll(inorderRide(node.getLeft()));
        inorderList.add(node.getData());
        inorderList.addAll(inorderRide(node.getRight()));

        return inorderList;
    }

    public ArrayList<T> getPreorder() {
        return preorderRide(radix);
    }

    private ArrayList<T> preorderRide(Node<T> node) {
        ArrayList<T> preorderList = new ArrayList<T>();
        if (node == null)
            return preorderList;
        preorderList.add(node.getData());
        preorderList.addAll(preorderRide(node.getLeft()));
        preorderList.addAll(preorderRide(node.getRight()));;
        return preorderList;
    }

    public ArrayList<T> getPostorder() {
        return postOrderRide(radix);
    }

    private ArrayList<T> postOrderRide(Node<T> node) {
        ArrayList<T> postOrderList = new ArrayList<T>();
        if (node == null)
            return postOrderList;

        postOrderList.addAll(postOrderRide(node.getLeft()));
        postOrderList.addAll(postOrderRide(node.getRight()));;
        postOrderList.add(node.getData());
        return postOrderList;
    }
}