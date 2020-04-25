package com.clownfish7.concurrency.part1.mylock;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author You
 * @create 2020-04-25 22:01
 */
public class BooleanLockTest {

    @Test
    public void test() throws InterruptedException {
        final BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4")
                .forEach(name ->
                        new Thread(() -> {
                            try {
                                booleanLock.lock(100L);
                                Optional.of(Thread.currentThread().getName() + " have the lock Monitor")
                                        .ifPresent(System.out::println);
                                work();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (Lock.TimeOutException e) {
                                Optional.of(Thread.currentThread().getName() + " time out")
                                        .ifPresent(System.out::println);
                            } finally {
                                booleanLock.unlock();
                            }
                        }, name).start()
                );
        Thread.currentThread().join();
    }

    private void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is Working...")
                .ifPresent(System.out::println);
        Thread.sleep(50_000);
    }
}
