package com.clownfish7.concurrency.part1.mylock;

import java.util.Collection;

/**
 * @author You
 * @create 2020-04-25 21:55
 */
public interface Lock {

    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();
}
