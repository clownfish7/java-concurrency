package com.clownfish7.concurrency.part3.collections.blocking;

import java.util.concurrent.SynchronousQueue;

/**
 * @author You
 * @create 2020-05-05 0:59
 * {@link SynchronousQueue}
 */
public class SynchornousQueueTest {

    /**
     * 无容量
     * 生产线程添加一个进 queue 时必须有一个消费线程在拿，反之亦然
     * amazing!?
     */

    private SynchronousQueue<Integer> queue = new SynchronousQueue<>();
}
