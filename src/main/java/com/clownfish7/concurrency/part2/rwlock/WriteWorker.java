package com.clownfish7.concurrency.part2.rwlock;

import java.util.Random;

/**
 * @author You
 * @create 2020-05-01 18:36
 */
public class WriteWorker extends Thread {

    private static final Random random = new Random(System.currentTimeMillis());

    private final SharedData data;

    private final String filler;

    private int index = 0;

    public WriteWorker(SharedData data, String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                data.write(nextChar());
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private char nextChar() {
        char c = filler.charAt(index);
        index++;
        if (index >= filler.length()) {
            index = 0;
        }
        return c;
    }
}
