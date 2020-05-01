package com.clownfish7.concurrency.part2.observer.demo;

public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Octal String:" + Integer.toOctalString(subject.getState()));
    }
}