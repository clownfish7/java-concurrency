package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author You
 * @create 2020-05-02 19:38
 */
public class AtomicIntegerTest {

    AtomicInteger atomicInteger = new AtomicInteger();

    @Test
    public void testCreate() {

        assert atomicInteger.get() == 0 : "error!";
        atomicInteger = new AtomicInteger(10);
        assert atomicInteger.get() == 10 : "error!";
    }

    @Test
    public void testSet() {
        atomicInteger.set(20);
        assert atomicInteger.get() == 20 : "error!";
        atomicInteger.lazySet(30);
        assert atomicInteger.get() == 30 : "error!";
    }

    @Test
    public void testGetAndSet() {
        assert atomicInteger.getAndSet(10) == 0 : "error!";
        assert atomicInteger.getAndSet(20) == 10 : "error!";
    }

    @Test
    public void testCompareAndSet() {
        atomicInteger.compareAndSet(1, 2);
        assert atomicInteger.get() == 0 : "error!";
        atomicInteger.compareAndSet(0, 2);
        assert atomicInteger.get() == 2 : "error!";
    }


}
