package com.clownfish7.concurrency.part3.phaser;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-05 19:23
 */
public class PhaserTest {

    /**
     * arrive no block
     */

    private Phaser phaser = new Phaser();

    @Test
    public void testRegister() throws InterruptedException {
        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            try {
                phaser.register();
                System.out.println(Thread.currentThread().getName() + " - working!");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - keep going!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            try {
                phaser.register();
                System.out.println(Thread.currentThread().getName() + " - working!");
                TimeUnit.SECONDS.sleep(7);
                System.out.println(Thread.currentThread().getName() + " - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - keep going!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        phaser.register();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        System.out.println("all done!");
        TimeUnit.SECONDS.sleep(15);
    }


    @Test
    public void testReuse() throws InterruptedException {
        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            try {
                phaser.register();
                System.out.println(Thread.currentThread().getName() + " - 1 - working!");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " - 1 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 1 - keep going!");

                System.out.println(Thread.currentThread().getName() + " - 2 - working!");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " - 2 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 2 - keep going!");

                System.out.println(Thread.currentThread().getName() + " - 3 - working!");
                TimeUnit.SECONDS.sleep(7);
                System.out.println(Thread.currentThread().getName() + " - 3 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 3 - keep going!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            try {
                phaser.register();
                System.out.println(Thread.currentThread().getName() + " - 1 - working!");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " - 1 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 1 - keep going!");

                System.out.println(Thread.currentThread().getName() + " - 2 - working!");
                TimeUnit.SECONDS.sleep(7);
                System.out.println(Thread.currentThread().getName() + " - 2 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 2 - keep going!");

                System.out.println(Thread.currentThread().getName() + " - 3 - working!");
                TimeUnit.SECONDS.sleep(9);
                System.out.println(Thread.currentThread().getName() + " - 3 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 3 - keep going!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        phaser.register();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        System.out.println(" 1 - all done!");
        TimeUnit.SECONDS.sleep(10);
        phaser.arriveAndAwaitAdvance();
        System.out.println(" 2 - all done!");
        TimeUnit.SECONDS.sleep(15);
        phaser.arriveAndAwaitAdvance();
        System.out.println(" 3 - all done!");
        TimeUnit.SECONDS.sleep(15);
    }


    @Test
    public void testDeregister() throws InterruptedException {
        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            try {
                phaser.register();
                System.out.println(Thread.currentThread().getName() + " - 1 - working!");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " - 1 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 1 - keep going!");

                System.out.println(Thread.currentThread().getName() + " - 2 - working!");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " - 2 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 2 - keep going!");

                System.out.println(Thread.currentThread().getName() + " - 3 - working!");
                TimeUnit.SECONDS.sleep(7);
                System.out.println(Thread.currentThread().getName() + " - 3 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 3 - keep going!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        IntStream.range(0, 5).forEach(i -> new Thread(() -> {
            try {
                phaser.register();
                System.out.println(Thread.currentThread().getName() + " - 1 - working!");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " - 1 - done!");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " - 1 - keep going!");

                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
        phaser.register();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        System.out.println(" 1 - all done!");
        TimeUnit.SECONDS.sleep(10);
        phaser.arriveAndAwaitAdvance();
        System.out.println(" 2 - all done!");
        TimeUnit.SECONDS.sleep(15);
        phaser.arriveAndAwaitAdvance();
        System.out.println(" 3 - all done!");
        TimeUnit.SECONDS.sleep(15);
    }


    @Test
    public void testGet() {
        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.isTerminated());
        System.out.println("-----------------------");

        phaser.register();
        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.isTerminated());
        System.out.println("-----------------------");

        phaser.arriveAndAwaitAdvance();
        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());
        System.out.println(phaser.getUnarrivedParties());
        System.out.println(phaser.isTerminated());
        System.out.println("-----------------------");
    }


    @Test
    public void testTerminated() {
        System.out.println(phaser.isTerminated());
        phaser.forceTermination();
        System.out.println(phaser.isTerminated());
    }

    @Test
    public void testAwaitAdvance() {
        // 阻塞住等待全部 arrive 但不参与进 phaser
        phaser.awaitAdvance(phaser.getPhase());
    }

    @Test
    public void testAwaitAdvanceInterruptibly() throws InterruptedException, TimeoutException {
        // 阻塞住等待其他 arrive 可以中断，默认不可中断  如果传入其他phase则立即返回
        System.out.println("1");
        phaser.awaitAdvanceInterruptibly(12);
        System.out.println("1");
        // 超时抛出 Timeout
        phaser.awaitAdvanceInterruptibly(11, 10, TimeUnit.SECONDS);
        System.out.println("1");
    }


    @Test
    public void testSome() {
        Phaser p = new Phaser();
        p.register();
        System.out.println("123");
        p.arriveAndAwaitAdvance();
        System.out.println("321");


        System.out.println("result : " + testTryCatch());
    }

    public int testTryCatch() {
        try {
            System.out.println("step :" + 1);
            int a = 1 / 0;
            System.out.println("step :" + 2);
            return 1;
        } catch (Exception e) {
            System.out.println("step :" + 3);
            return 2;
        } finally {
            System.out.println("step :" + 4);
            return 3;
        }
    }
}
