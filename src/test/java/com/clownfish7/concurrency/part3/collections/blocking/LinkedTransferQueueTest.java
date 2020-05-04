package com.clownfish7.concurrency.part3.collections.blocking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-05 2:38
 * {@link LinkedTransferQueue}
 */
public class LinkedTransferQueueTest {

    /**
     * 和 SynchornousQueue 不同的是没被 consumer 拿走就一直 block
     * SynchornousQueue 没被拿走就丢弃了 不管了
     * 适用需要确定一定被消费的场景，传统只管往 queue 中扔，消费与否于己无关
     */

    private LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();

    @Test
    public void testAdd() {
        Assertions.assertTrue(queue.add(1));
        Assertions.assertEquals(queue.size(), 1);
    }

    @Test
    public void testTryTransfer() {
        // 有 consumer 在等就 true else false  will not into queue
        Assertions.assertFalse(queue.tryTransfer(1));
        Assertions.assertEquals(queue.size(), 0);
    }

    @Test
    public void testTransfer() throws InterruptedException {
        // block
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, TimeUnit.SECONDS);
        queue.transfer(1);
        Assertions.assertEquals(queue.size(), 0);
    }

    @Test
    public void testWaitingConsumer() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.range(0, 5).forEach(i -> {
            executorService.submit(() -> {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertTrue(queue.hasWaitingConsumer());
        Assertions.assertEquals(queue.getWaitingConsumerCount(),5);
    }
}
