package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author You
 * @create 2020-05-02 19:38
 */
public class AtomicLongTest {

    AtomicLong atomicLong = new AtomicLong();

    /**
     * 32
     * Long 64
     *
     * high 32
     * low  32
     */

    @Test
    public void testCreate() {
        assert atomicLong.get() == 0L : "error!";
    }

}
