package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:43
 */
public class SchedulerThread extends Thread {

    private final ActivationQueue activationQueue;

    public SchedulerThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    public void invoke(MethodRequest methodRequest) {
        this.activationQueue.put(methodRequest);
    }

    @Override
    public void run() {
        while (true) {
            activationQueue.take().execute();
        }
    }
}
