package com.clownfish7.concurrency.part2.singleton;

/**
 * @author You
 * @create 2020-05-01 12:44
 */
public class SingletonObjectEnum {

    private SingletonObjectEnum() {
        // empty
    }

    private enum Singleton {
        INSTENCE;

        private final SingletonObjectEnum instence;

        Singleton(){
            instence = new SingletonObjectEnum();
        }

        public SingletonObjectEnum getInstence() {
            return instence;
        }
    }

    public static SingletonObjectEnum getInstance() {
        return Singleton.INSTENCE.getInstence();
    }
}
