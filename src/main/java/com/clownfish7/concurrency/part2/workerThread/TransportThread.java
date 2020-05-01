package com.clownfish7.concurrency.part2.workerThread;

import java.util.Random;

public class TransportThread extends Thread {
    private final Channel channel;

    private static final Random random = new Random(System.currentTimeMillis());

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; ; i++) {
                Request request = new Request(getName(), i);
                this.channel.put(request);
                Thread.sleep(random.nextInt(1_000));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}