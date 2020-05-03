package com.clownfish7.concurrency.part3.lock;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author You
 * @create 2020-05-03 20:21
 */
public class ConditionTest {

    ReentrantLock reentrantLock = new ReentrantLock(true);
    LinkedList<String> list = new LinkedList<>();
    Condition PRODUCER = reentrantLock.newCondition();
    Condition CONSUMER = reentrantLock.newCondition();

    @Test
    public void test() throws InterruptedException {

        new Thread(() -> {
            while (true) {
                this.put();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                this.put();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                this.put();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                this.take();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                this.take();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                this.take();
            }
        }).start();

        Thread.currentThread().join();
    }

    private void put() {
        try {
            reentrantLock.lock();
            while (list.size() >= 100) {
                PRODUCER.await();
            }
            Thread.sleep(2000);
            System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + "put");
            list.add(System.currentTimeMillis() + Thread.currentThread().getName());
            CONSUMER.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }

    }

    private void take() {
        try {
            reentrantLock.lock();
            while (list.size() <= 0) {
                CONSUMER.await();
            }
            Thread.sleep(2000);
            System.out.println(list.removeFirst() + "get");
            PRODUCER.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}
