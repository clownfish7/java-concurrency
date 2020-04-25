package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-25 17:36
 */
public class ThreadCloseGracefulTest {

    private class Worker extends Thread {
        private volatile boolean start = true;
        @Override
        public void run() {
            while (start) {
                //
            }
        }
        public void shutdown() {
            this.start = false;
        }
    }

    @Test
    public void test() {
        Worker worker = new Worker();
        worker.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.shutdown();
    }

    private class Worker2 extends Thread {
        @Override
        public void run() {
            while (true) {
                if (Thread.interrupted())
                    break;
            }
            //-------------
            //-------------
            //-------------
        }
    }

    @Test
    public void test2() {
        Worker2 worker = new Worker2();
        worker.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.interrupt();
    }

}
