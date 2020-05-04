package com.clownfish7.concurrency.part3.collections.blocking;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-05 2:04
 */
public class DelayElement<T> implements Delayed {

    private T value;
    private long expireTime;

    public DelayElement(T value, long expireTime) {
        this.value = value;
        this.expireTime = System.currentTimeMillis() + expireTime;
    }

    public static <T> DelayElement<T> of(T value, long expireTime) {
        return new DelayElement<>(value, expireTime);
    }

    public T getValue() {
        return value;
    }

    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public String toString() {
        return "DelayElement{" +
                "value=" + value +
                ", expireTime=" + expireTime +
                '}';
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = expireTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayElement that = (DelayElement) o;
        return Long.compare(this.expireTime, that.getExpireTime());
    }
}