package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author You
 * @create 2020-05-02 19:38
 */
public class AtomicBooleanTest {

    AtomicBoolean atomicBoolean = new AtomicBoolean();

    @Test
    public void testCreate() {
        assert !atomicBoolean.get() : "error!";
        atomicBoolean = new AtomicBoolean(true);
        assert atomicBoolean.get() : "error!";
    }

    @Test
    public void testSet() {
        atomicBoolean.set(true);
        assert atomicBoolean.get() : "error!";
        atomicBoolean.lazySet(false);
        assert !atomicBoolean.get() : "error!";
    }

    @Test
    public void testGetAndSet() {
        assert !atomicBoolean.getAndSet(true) : "error!";
        assert atomicBoolean.get() : "error!";
    }

    @Test
    public void testCompareAndSet() {
        assert atomicBoolean.compareAndSet(false, true) : "error!";
        assert !atomicBoolean.compareAndSet(false, true) : "error!";
    }

}
