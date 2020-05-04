package com.clownfish7.concurrency.part3.collections.custom;

/**
 * @author You
 * @create 2020-05-04 18:14
 */
public class PriorityLinkedList<E extends Comparable<E>> {

    private static final String PLAIN_NULL = "null";

    private Node<E> first;

    private int size;

    public PriorityLinkedList() {
        this.first = null;
    }

    public static <E extends Comparable<E>> PriorityLinkedList<E> of(E... elements) {
        PriorityLinkedList<E> linkedList = new PriorityLinkedList<>();
        if (elements.length > 0) {
            for (E element : elements) {
                linkedList.addFirst(element);
            }
        }
        return linkedList;
    }

    public PriorityLinkedList<E> addFirst(E e) {
        final Node<E> node = new Node<>(e);
        Node<E> previous = null;
        Node<E> current = first;
        while (current != null && e.compareTo(current.value) > 0) {
            previous = current;
            current = current.next;
        }
        if (previous == null) {
            node.next = first;
            first = node;
        } else {
            previous.next = node;
            node.next = current;
        }
        size++;
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
