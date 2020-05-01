package com.clownfish7.concurrency.part2.singleton;

/**
 * @author You
 * @create 2020-05-01 12:44
 */
public class SingletonObjectLazy {

    public static volatile SingletonObjectLazy INSTENCE;

    private SingletonObjectLazy() {
        // empty
    }

    public SingletonObjectLazy getInstence() {
        if (INSTENCE == null) {
            synchronized (this) {
                if (INSTENCE == null) {
                    INSTENCE = new SingletonObjectLazy();
                }
            }
        }
        return INSTENCE;
    }
}
