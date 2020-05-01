package com.clownfish7.concurrency.part2.threadlocal.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 始终已当前线程作为Key值
 *
 * @param <T>
 */
class ThreadLocalSimulator<T> {

    private final Map<Thread, T> storage = new HashMap<>();

    public void set(T t) {
        synchronized (this) {
            Thread key = Thread.currentThread();
            storage.put(key, t);
        }
    }


    public T get() {
        synchronized (this) {
            Thread key = Thread.currentThread();
            T value = storage.get(key);
            if (value == null) {
                return initialValue();
            }
            return value;
        }
    }

    public T initialValue() {
        return null;
    }
}