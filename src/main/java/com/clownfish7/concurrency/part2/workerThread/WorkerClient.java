package com.clownfish7.concurrency.part2.workerThread;

public class WorkerClient {
    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        channel.startWorkers();

        new TransportThread("Alex", channel).start();
        new TransportThread("Jack", channel).start();
        new TransportThread("William", channel).start();
    }
}