package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author You
 * @create 2020-04-25 23:09
 */
public class ThreadGroupTest {

    @Test
    public void testThreadGroupCreat() throws InterruptedException {
        //use the name
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                while (true) {
                    try {
//                        System.out.println(getThreadGroup().getName());
//                        System.out.println(getThreadGroup().getParent());
//                        System.out.println(getThreadGroup().getParent().activeCount());
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t1.start();
//        t1.join();


        ThreadGroup tg2 = new ThreadGroup("TG2");
        Thread t2 = new Thread(tg2, "T2") {
            @Override
            public void run() {
                System.out.println(">>>" + tg1.getName());
                Thread[] threads = new Thread[tg1.activeCount()];
                tg1.enumerate(threads);

                Arrays.asList(threads).forEach(System.out::println);
            }
        };

        t2.start();

        System.out.println(tg2.getName());
        System.out.println(tg2.getParent());

        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getThreadGroup().getName());
    }


    @Test
    public void testThreadGroupApi() throws InterruptedException {
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup(tg1, "TG2");
        Thread t2 = new Thread(tg2, "T2") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        t2.start();

        // 获得当前线程组中线程数目， 包括可运行和不可运行的
        System.out.println(tg1.activeCount());
        //获得当前线程组中活动的子线程组的数目
        System.out.println(tg1.activeGroupCount());
        t2.checkAccess();

        // 线程组不是空的或者已经destroy会抛出异常
//        tg1.destroy();

        System.out.println("=========================");
        Thread[] ts1 = new Thread[tg1.activeCount()];
        //列举当前线程组中的线程
        tg1.enumerate(ts1);
        Arrays.asList(ts1).forEach(System.out::println);

        System.out.println("=========================");
        tg1.enumerate(ts1, true);   // true 递归
        Arrays.asList(ts1).forEach(System.out::println);

        System.out.println("=========================");
        ts1 = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(ts1, false);
        Arrays.asList(ts1).forEach(System.out::println);

        // 打断所有组内线程
        tg1.interrupt();
    }

    @Test
    public void testThreadGroupApiDaemon() throws InterruptedException {
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "t1") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 线程组内线程都执行完后自动销毁
        tg1.setDaemon(true);
        t1.start();
        Thread.sleep(2_000);
        System.out.println(tg1.isDestroyed());
//        tg1.destroy();
//        System.out.println(tg1.isDestroyed());
    }

    /**
     *
     * 线程组信息的获取
     * public int activeCount();                    // 获得当前线程组中线程数目， 包括可运行和不可运行的
     * public int activeGroupCount();               //获得当前线程组中活动的子线程组的数目
     * public int enumerate(Thread list[]);         //列举当前线程组中的线程
     * public int enumerate(ThreadGroup list[]);    //列举当前线程组中的子线程组
     * public final int getMaxPriority();           //获得当前线程组中最大优先级
     * public final String getName();               //获得当前线程组的名字
     * public final ThreadGroup getParent();        //获得当前线程组的父线程组
     * public boolean parentOf(ThreadGroup g);      //判断当前线程组是否为指定线程的父线程
     * public boolean isDaemon();                   //判断当前线程组中是否有监护线程
     * public void list();                          //列出当前线程组中所有线程和子线程名
     *
     * 线程组的操作
     * public final void resume();                  //使被挂起的当前组内的线程恢复到可运行状态
     * public final void setDaemon (boolean daemon);//指定一个线程为当前线程组的监护线程
     * public final void setMaxPriority(int pri);   //设置当前线程组允许的最大优先级
     * public final void stop();                    //终止当前线程组中所有线程
     * public final void suspend();                 //挂起当前线程组中所有线程
     * public String toStrinng();                   //将当前线程组转换为String类的对象
     */
}

