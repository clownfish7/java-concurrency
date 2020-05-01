package com.clownfish7.concurrency.part2.singleton;

/**
 * @author You
 * @create 2020-05-01 12:44
 */
public class SingletonObjectHolder {

    private SingletonObjectHolder() {
        // empty
    }

    public static SingletonObjectHolder getInstence() {
        return InstenceHolder.INSTENSE;
    }

    private static class InstenceHolder {
        private static final SingletonObjectHolder INSTENSE = new SingletonObjectHolder();
    }
}
