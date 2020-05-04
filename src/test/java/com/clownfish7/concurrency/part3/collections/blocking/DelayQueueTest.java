package com.clownfish7.concurrency.part3.collections.blocking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.DelayQueue;

/**
 * @author You
 * @create 2020-05-05 1:12
 * {@link DelayQueue}
 */
public class DelayQueueTest {

    /**
     * 需要实现 Delayed 接口
     */

    private DelayQueue<DelayElement> queue = new DelayQueue<>();

    @Test
    public void test() {
        queue.add(DelayElement.of("d1", 2000));
        queue.add(DelayElement.of("d2", 8000));
        queue.add(DelayElement.of("d3", 6000));
        for (DelayElement delayElement : queue) {
            System.out.println(delayElement);
        }
    }

    @Test
    public void testCompare() {
        queue.add(DelayElement.of("d1", 9000));
        queue.add(DelayElement.of("d2", 4000));
        queue.add(DelayElement.of("d3", 6000));
        Assertions.assertEquals(queue.peek().getValue(), "d2");
    }

    @Test
    public void testTake() throws InterruptedException {
        queue.add(DelayElement.of("d1", 2000));
        long begin = System.currentTimeMillis();
        Assertions.assertEquals(queue.take().getValue(), "d1");
        Assertions.assertTrue(System.currentTimeMillis() - begin > 2000);
    }


}
