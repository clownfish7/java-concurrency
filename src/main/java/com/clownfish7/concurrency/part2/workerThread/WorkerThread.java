package com.clownfish7.concurrency.part2.workerThread;

import java.util.Random;

/**
 * @author You
 * @create 2020-05-01 23:37
 */
public class WorkerThread extends Thread {

    private final Channel channel;

    private static final Random random = new Random(System.currentTimeMillis());

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            channel.take().execute();
            try {
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
