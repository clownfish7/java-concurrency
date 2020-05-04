package com.clownfish7.concurrency.part3.collections;

/**
 * @author You
 * @create 2020-05-04 18:14
 */
public class LinkedList<E> {

    private static final String PLAIN_NULL = "null";

    private Node<E> first;

    private int size;

    public LinkedList() {
        this.first = null;
    }

    public static <E> LinkedList<E> of(E... elements) {
        LinkedList<E> linkedList = new LinkedList<>();
        if (elements.length > 0) {
            for (E element : elements) {
                linkedList.addFirst(element);
            }
        }
        return linkedList;
    }

    public LinkedList<E> addFirst(E e) {
        final Node<E> node = new Node<>(e);
        node.next = first;
        size++;
        first = node;
        return this;
    }

    public E removeFirst() {
        if (!isEmpty()) {
            Node<E> current = first;
            first = first.next;
            size--;
            return current.value;
        }
        return null;
    }

    public boolean contains(E e) {
        Node<E> current = first;
        while (null != current) {
            if (current.value == e) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private static class Node<E> {
        E value;
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (null != value) {
                return value.toString();
            }
            return PLAIN_NULL;
        }
    }
}
