package com.clownfish7.concurrency.part2.observer;

import java.util.Arrays;


public class ThreadLifeCycleClient {
    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}