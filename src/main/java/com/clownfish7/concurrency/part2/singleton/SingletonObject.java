package com.clownfish7.concurrency.part2.singleton;

/**
 * @author You
 * @create 2020-05-01 12:44
 */
public class SingletonObject {

    public static final SingletonObject INSTENCE = new SingletonObject();

    private SingletonObject() {
        // empty
    }

    public SingletonObject getInstence() {
        return INSTENCE;
    }
}
