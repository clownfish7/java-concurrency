package com.clownfish7.concurrency.part3.cyclicbarrrier;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author You
 * @create 2020-05-03 15:56
 */
public class CyclicBarrierTest {

    /**
     * CountDownLatch vs CyclicBarrier
     * CountDownLatch不能reset CyclicBarrier可以循环使用
     * 工作线程之间互不关心，工作线程必须等到同一个共同点才去执行某一个动作
     */

    @Test
    public void test() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            System.out.println("every is ok!");
        });

        Thread t1 =new Thread(() -> {
            try {
                System.out.println("t1 ok1!");
                Thread.sleep(1000);
                System.out.println("t1 ok2!");
                cyclicBarrier.await();
                System.out.println("t1 other ok!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Thread t2 =new Thread(() -> {
            try {
                System.out.println("t2 ok1!");
                Thread.sleep(2000);
                System.out.println("t2 ok2!");
                cyclicBarrier.await();
                System.out.println("t2 other ok!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        Thread.sleep(1500);

        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.isBroken());

        cyclicBarrier.reset();
        Thread.sleep(2500);

        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.isBroken());

        Thread.sleep(5000);
    }
}
