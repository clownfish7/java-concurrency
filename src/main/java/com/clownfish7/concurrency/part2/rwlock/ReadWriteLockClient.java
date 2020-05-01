package com.clownfish7.concurrency.part2.rwlock;

/**
 * @author You
 * @create 2020-05-01 18:42
 */
public class ReadWriteLockClient {
    public static void main(String[] args) {
        SharedData sharedData = new SharedData(10);
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new WriteWorker(sharedData, "afsgsrgagr").start();
    }
}
