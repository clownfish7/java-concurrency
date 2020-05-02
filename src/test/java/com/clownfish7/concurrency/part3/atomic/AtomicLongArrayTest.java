package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * @author You
 * @create 2020-05-03 0:40
 */
public class AtomicLongArrayTest {

    AtomicLongArray atomicLongArray = new AtomicLongArray(10);

    @Test
    public void testCreate() {
        assert atomicLongArray.length() == 10 : "error!";
    }

    @Test
    public void testGet() {
        assert atomicLongArray.get(5) == 0 : "error!";
    }

    @Test
    public void testSet() {
        atomicLongArray.set(5, 10);
        assert atomicLongArray.get(5) == 10 : "error!";
    }

    @Test
    public void testGetAndSet() {
        assert atomicLongArray.getAndSet(5, 10) == 0 : "error!";
        assert atomicLongArray.get(5) == 10 : "error!";
    }

    @Test
    public void testGetAndAccumulate() {
        atomicLongArray.set(5, 10);
        atomicLongArray.getAndAccumulate(5, 1, (l, r) -> l + r);
        assert atomicLongArray.get(5) == 11 : "error!";
    }
}
