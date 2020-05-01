package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:44
 */
public final class ActiveObjectFactory {

    private ActiveObjectFactory() {

    }

    public static ActiveObject createActiveObject() {

        Servant servant = new Servant();
        ActivationQueue activationQueue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(activationQueue);
        schedulerThread.start();
        return new ActiveObjectProxy(servant, schedulerThread);
    }

}
