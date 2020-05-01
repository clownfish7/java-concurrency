package com.clownfish7.concurrency.part2.future;

public interface Future<T> {

    T get() throws InterruptedException;

}