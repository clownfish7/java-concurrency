package com.clownfish7.concurrency.part2.threadlocal.demo;


public class ThreadLocalSimpleTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "clownfish7";
        }
    };

    //JVM start main thread
    public static void main(String[] args) throws InterruptedException {
//        threadLocal.set("Alex");
        Thread.sleep(1000);
        String value = threadLocal.get();
        System.out.println(value);
    }
}