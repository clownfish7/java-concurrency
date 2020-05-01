package com.clownfish7.concurrency.part2.workerThread;

/**
 * @author You
 * @create 2020-05-01 23:35
 */
public class Request {

    private final String name;
    private final int number;

    public Request(final String name, final int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executed " + this);
    }

    @Override
    public String toString() {
        return "Request=> No. " + number + " Name. " + name;
    }
}
