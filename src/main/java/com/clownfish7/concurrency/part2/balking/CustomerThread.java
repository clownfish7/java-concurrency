package com.clownfish7.concurrency.part2.balking;

import java.io.IOException;
import java.util.Random;

public class CustomerThread extends Thread {

    private final BalkingData balkingData;

    private final Random random = new Random(System.currentTimeMillis());

    public CustomerThread(BalkingData balkingData) {
        super("Customer");
        this.balkingData = balkingData;
    }

    @Override
    public void run() {
        try {
            balkingData.save();
            for (int i = 0; i < 20; i++) {
                balkingData.change("No." + i);
                Thread.sleep(random.nextInt(1000));
                balkingData.save();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}