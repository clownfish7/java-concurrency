package com.clownfish7.concurrency.part3.collections.blocking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-05 0:49
 * {@link LinkedBlockingDeque}
 */
public class LinkedBlockingDequeTest {

    private LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();

    @Test
    public void testAdd() {
        // 调用 offerLast add null 抛出异常 throw new NullPointerException();
        Assertions.assertTrue(deque.add(1));
        Assertions.assertTrue(deque.add(2));
        Assertions.assertTrue(deque.add(3));
        Assertions.assertEquals(deque.size(), 3);
    }

    @Test
    public void testOffer() {
        // offerLast add null 抛出异常 throw new NullPointerException();
        Assertions.assertTrue(deque.offer(1));
        Assertions.assertTrue(deque.offer(2));
        Assertions.assertTrue(deque.offer(3));
        Assertions.assertEquals(deque.size(), 3);
    }

    @Test
    public void testPut() throws InterruptedException {
        // never need to block
        // 调用 putLast add null 抛出异常 throw new NullPointerException();
        deque.put(1);
        deque.put(2);
        deque.put(3);
        Assertions.assertEquals(deque.size(), 3);
    }

    @Test
    public void testTake() throws InterruptedException {
        // block
        Integer take = deque.take();
    }

    @Test
    public void testPoll() throws InterruptedException {
        // not block
        Assertions.assertNull(deque.poll());
        // block
        Assertions.assertNull(deque.poll(2, TimeUnit.SECONDS));
    }

    @Test
    public void testPeek() {
        // null when queue is empty
        Assertions.assertNull(deque.peek());
        deque.add(1);
        deque.add(2);
        Assertions.assertEquals(deque.peek(), 1);
        Assertions.assertEquals(deque.peek(), 1);
    }

    @Test
    public void testElement() {
        // 调用 peek  if null 则抛异常
        deque.add(1);
        Assertions.assertEquals(deque.element(), 1);
        deque.clear();
        Assertions.assertThrows(NoSuchElementException.class, () -> deque.element());
    }

    @Test
    public void testRemove() {
        // 调用 pool  if null 则抛异常
        deque.add(1);
        Assertions.assertEquals(deque.remove(), 1);
        Assertions.assertThrows(NoSuchElementException.class, () -> deque.remove());
    }

    @Test
    public void testRemainingCapacity() {
        /**
         * return capacity - count.get();
         */
        Assertions.assertEquals(deque.remainingCapacity(), Integer.MAX_VALUE);
        deque.add(1);
        Assertions.assertEquals(deque.remainingCapacity(), Integer.MAX_VALUE - 1);
    }

    @Test
    public void testDrainTo() {
        // 排干到 collection
        ArrayList<Object> list = new ArrayList<>();
        deque.add(1);
        deque.add(2);
        Assertions.assertEquals(deque.drainTo(list), 2);
        Assertions.assertEquals(list.size(), 2);
        Assertions.assertEquals(deque.size(), 0);
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Assertions.assertEquals(deque.drainTo(list, 1), 1);
        Assertions.assertEquals(list.size(), 2 + 1);
        Assertions.assertEquals(deque.size(), 2);
    }
}
