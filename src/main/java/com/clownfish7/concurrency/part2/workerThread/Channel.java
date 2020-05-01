package com.clownfish7.concurrency.part2.workerThread;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-01 23:35
 */
public class Channel {

    private static final int MAX_QUEUE = 100;
    private final Request[] requestQueue;
    private final WorkerThread[] workerPool;
    private int head;
    private int tail;
    private int count;

    public Channel(int workers) {
        this(MAX_QUEUE, workers);
    }

    public Channel(int queueSize, int workers) {
        this.requestQueue = new Request[queueSize];
        this.workerPool = new WorkerThread[workers];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.init();
    }

    private void init() {
        IntStream.range(0, workerPool.length).forEach(i -> workerPool[i] = new WorkerThread("worker-" + i, this));
    }

    public void startWorkers() {
        Arrays.asList(workerPool).forEach(WorkerThread::start);
    }

    public synchronized void put(Request request) {
        while (count >= requestQueue.length) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.requestQueue[tail] = request;
        this.tail = (tail + 1) % requestQueue.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized Request take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Request request = this.requestQueue[head];
        this.head = (this.head + 1) % this.requestQueue.length;
        this.count--;
        this.notifyAll();
        return request;
    }
}
