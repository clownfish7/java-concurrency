package com.clownfish7.concurrency.part3.collections.blocking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-05 0:49
 * {@link LinkedBlockingQueue}
 */
public class LinkedBlockingQueueTest {

    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    @Test
    public void testAdd() {
        // 调用 offer add null 抛出异常 throw new NullPointerException();
        Assertions.assertTrue(queue.add(1));
        Assertions.assertTrue(queue.add(2));
        Assertions.assertTrue(queue.add(3));
        Assertions.assertEquals(queue.size(), 3);
    }

    @Test
    public void testOffer() {
        // add null 抛出异常 throw new NullPointerException();
        Assertions.assertTrue(queue.offer(1));
        Assertions.assertTrue(queue.offer(2));
        Assertions.assertTrue(queue.offer(3));
        Assertions.assertEquals(queue.size(), 3);
    }

    @Test
    public void testPut() throws InterruptedException {
        // never need to block
        // 调用 offer add null 抛出异常 throw new NullPointerException();
        queue.put(1);
        queue.put(2);
        queue.put(3);
        Assertions.assertEquals(queue.size(), 3);
    }

    @Test
    public void testTake() throws InterruptedException {
        // block
        Integer take = queue.take();
    }

    @Test
    public void testPoll() throws InterruptedException {
        // not block
        Assertions.assertNull(queue.poll());
        // block
        Assertions.assertNull(queue.poll(2, TimeUnit.SECONDS));
    }

    @Test
    public void testPeek() {
        // null when queue is empty
        Assertions.assertNull(queue.peek());
        queue.add(1);
        queue.add(2);
        Assertions.assertEquals(queue.peek(), 1);
        Assertions.assertEquals(queue.peek(), 1);
    }

    @Test
    public void testElement() {
        // 调用 peek  if null 则抛异常
        queue.add(1);
        Assertions.assertEquals(queue.element(), 1);
        queue.clear();
        Assertions.assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @Test
    public void testRemove() {
        // 调用 pool  if null 则抛异常
        queue.add(1);
        Assertions.assertEquals(queue.remove(), 1);
        Assertions.assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    @Test
    public void testRemainingCapacity() {
        /**
         * return capacity - count.get();
         */
        Assertions.assertEquals(queue.remainingCapacity(), Integer.MAX_VALUE);
        queue.add(1);
        Assertions.assertEquals(queue.remainingCapacity(), Integer.MAX_VALUE - 1);
    }

    @Test
    public void testDrainTo() {
        // 排干到 collection
        ArrayList<Object> list = new ArrayList<>();
        queue.add(1);
        queue.add(2);
        Assertions.assertEquals(queue.drainTo(list), 2);
        Assertions.assertEquals(list.size(), 2);
        Assertions.assertEquals(queue.size(), 0);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Assertions.assertEquals(queue.drainTo(list, 1), 1);
        Assertions.assertEquals(list.size(), 2 + 1);
        Assertions.assertEquals(queue.size(), 2);
    }
}
