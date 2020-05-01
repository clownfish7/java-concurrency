package com.clownfish7.concurrency.part2.observer.demo;

public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();

}
