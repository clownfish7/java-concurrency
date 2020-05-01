package com.clownfish7.concurrency.part2.activeObject;

import java.util.LinkedList;

/**
 * @author You
 * @create 2020-05-02 1:32
 */
public class ActivationQueue {

    private static final int MAX_METHOD_REQUEST_QUEUE_SIZE = 100;

    private final LinkedList<MethodRequest> methodRequests;

    public ActivationQueue() {
        this.methodRequests = new LinkedList<>();
    }

    public synchronized void put(MethodRequest methodRequest) {
        while (methodRequests.size() >= MAX_METHOD_REQUEST_QUEUE_SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        methodRequests.addLast(methodRequest);
        this.notifyAll();
    }

    public synchronized MethodRequest take() {
        while (methodRequests.size() <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MethodRequest methodRequest = methodRequests.removeFirst();
        this.notifyAll();
        return methodRequest;
    }
}
