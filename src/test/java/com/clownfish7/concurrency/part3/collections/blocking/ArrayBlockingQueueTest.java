package com.clownfish7.concurrency.part3.collections.blocking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-04 23:12
 * {@link ArrayBlockingQueue}
 */
public class ArrayBlockingQueueTest {

    /**
     * 1. This queue orders elements FIFO (first-in-first-out)
     * 2. Once created, the capacity cannot be changed
     */

    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

    @Test
    public void testAdd() {
        // java.lang.IllegalStateException: Queue full
        Assertions.assertTrue(queue.add(1));
        Assertions.assertTrue(queue.add(2));
        Assertions.assertTrue(queue.add(3));
        Assertions.assertThrows(IllegalStateException.class, () -> queue.add(4), "Queue full");
    }

    @Test
    public void testOffer() {
        Assertions.assertTrue(queue.offer(1));
        Assertions.assertTrue(queue.offer(2));
        Assertions.assertTrue(queue.offer(3));
        Assertions.assertFalse(queue.offer(4));
        Assertions.assertDoesNotThrow(() -> queue.offer(5));
    }

    @Test
    public void testPut() throws InterruptedException {
        /**
         * put 在 queue full 的时候会阻塞住
         */
        queue.put(1);
        queue.put(2);
        queue.put(3);
        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        threadExecutor.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        queue.put(4);
        System.out.println(queue);
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
        // 剩余容量 return items.length - count;
        Assertions.assertEquals(queue.remainingCapacity(), 3);
        queue.add(1);
        Assertions.assertEquals(queue.remainingCapacity(), 2);
    }

    @Test
    public void testDrainTo() {
        // 排干到 collection
        ArrayList<Object> list = new ArrayList<>();
        queue.add(1);
        queue.add(2);
        Assertions.assertEquals(queue.drainTo(list), 2);
        Assertions.assertEquals(list.size(), 2);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        Assertions.assertEquals(queue.drainTo(list, 22), 3);
        Assertions.assertEquals(list.size(), 3 + 2);
    }
}
