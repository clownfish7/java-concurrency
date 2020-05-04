package com.clownfish7.concurrency.part3.collections;

import java.util.Random;

/**
 * @author You
 * @create 2020-05-04 20:59
 */
public class SkipLinkedList<E extends Comparable<E>> {

    private final static byte HEAD_NODE = (byte) -1;
    private final static byte DATA_NODE = (byte) 0;
    private final static byte TAIL_NODE = (byte) -1;

    private int size;
    private int height;
    private Node<E> head;
    private Node<E> tail;
    private Random random;

    public SkipLinkedList() {
        this.head = new Node<E>(null, HEAD_NODE);
        this.tail = new Node<E>(null, TAIL_NODE);
        head.right = tail;
        tail.left = head;
        random = new Random(System.currentTimeMillis());
    }

    private Node<E> find(E e) {
        Node<E> current = head;
        for (; ; ) {
            while (current.right.bit != TAIL_NODE && current.right.value.compareTo(e) <= 0) {
                current = current.right;
            }
            if (current.down != null) {
                current = current.down;
            } else {
                break;
            }
        }
        return current; // current < e < current.right (if exists)
    }

    public void add(E e) {
        Node<E> node = new Node<>(e);
        Node<E> nearNode = find(e);
        node.right = nearNode.right;
        nearNode.right.left = node;
        nearNode.right = node;
        node.left = nearNode;

        int currentLevel = 0;
        while (random.nextDouble() < 0.3D) {
            if (currentLevel >= height) {
                height++;
                Node<E> headNode = new Node<>(null, HEAD_NODE);
                Node<E> tailNode = new Node<>(null, TAIL_NODE);
                headNode.right = tailNode;
                tailNode.left = headNode;
                headNode.down = head;
                head.up = headNode;
                tailNode.down = tail;
                tail.up = tailNode;

                head = headNode;
                tail = tailNode;
            }

            while (nearNode != null && nearNode.up == null) {
                nearNode = nearNode.left;
            }
            nearNode = nearNode.up;
            Node<E> upNode = new Node<>(e);
            upNode.left = nearNode;
            upNode.right = nearNode.right;
            upNode.down = node;

            nearNode.right.left = upNode;
            nearNode.right = upNode;

            node.up = upNode;
            node = upNode;
            currentLevel++;

        }
        size++;
    }

    public boolean contains(E e) {
        return find(e).value == e;
    }

    public E get(E e) {
        Node<E> node = find(e);
        return node.value == e ? node.value : null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int hight() {
        return height;
    }

    public void dump() {
        Node<E> level = head;
        Node<E> current;
        for (int i = height; i >= 0; i--) {
            current = level;
            System.out.print("[" + i + "] : head ");
            while (current.right.bit != -1) {
                current = current.right;
                System.out.print("->");
                System.out.print(current.value+"");
            }
            level = level.down;
            System.out.println(" -> tail");
        }
    }

    private static class Node<E> {
        private E value;
        private Node<E> up, down, left, right;
        private byte bit;

        public Node(E value) {
            this(value, DATA_NODE);
        }

        public Node(E value, byte bit) {
            this.value = value;
            this.bit = bit;
        }
    }
}
