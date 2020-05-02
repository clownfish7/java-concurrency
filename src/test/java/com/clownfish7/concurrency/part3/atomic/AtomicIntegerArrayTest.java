package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author You
 * @create 2020-05-03 0:40
 */
public class AtomicIntegerArrayTest {

    AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);

    @Test
    public void testCreate() {
        assert atomicIntegerArray.length() == 10 : "error!";
    }

    @Test
    public void testGet() {
        assert atomicIntegerArray.get(5) == 0 : "error!";
    }

    @Test
    public void testSet() {
        atomicIntegerArray.set(5, 10);
        assert atomicIntegerArray.get(5) == 10 : "error!";
    }

    @Test
    public void testGetAndSet() {
        assert atomicIntegerArray.getAndSet(5, 10) == 0 : "error!";
        assert atomicIntegerArray.get(5) == 10 : "error!";
    }

    @Test
    public void testGetAndAccumulate() {
        atomicIntegerArray.set(5, 10);
        atomicIntegerArray.getAndAccumulate(5, 1, (l, r) -> l + r);
        assert atomicIntegerArray.get(5) == 11 : "error!";
    }
}
