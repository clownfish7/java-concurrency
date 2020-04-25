package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * @author You
 * @create 2020-04-25 21:08
 * 对于所有多线程应用程序，我们需要确保行为一致的规则：
 * <p>
 * 相互排斥 - 一次只有一个线程执行一个关键部分
 * 可见性 - 一个线程对共享数据所做的更改对其他线程可见，以维护数据一致性
 * 同步方法和块提供上述两种属性，但代价是牺牲应用程序的性能。
 * <p>
 * volatile是一个非常有用的关键字，因为它可以帮助确保数据变化的可见性方面，当然，不提供互斥。因此，在多线程并行执行代码块但需要确保可见性的情况下，它非常有用。
 */
public class ProduceConsumerTest {

    private int i = 1;

    private static final Object LOCK = new Object();

    private volatile boolean isProduced = false;

    @Test
    public void testV1() {
        ProduceConsumerTest pc = new ProduceConsumerTest();

        new Thread("P") {
            @Override
            public void run() {
                while (true)
                    pc.produceV1();
            }
        }.start();

        new Thread("C") {
            @Override
            public void run() {
                while (true)
                    pc.consumeV1();
            }
        }.start();
    }

    private void produceV1() {
        synchronized (LOCK) {
            System.out.println("P->" + (i++));
        }
    }

    private void consumeV1() {
        synchronized (LOCK) {
            System.out.println("C->" + i);
        }
    }


    @Test
    public void testV2() {
        ProduceConsumerTest pc = new ProduceConsumerTest();
        Stream.of("P1", "P2").forEach(n ->
                new Thread(n) {
                    @Override
                    public void run() {
                        while (true)
                            pc.produceV2();
                    }
                }.start()
        );
        Stream.of("C1", "C2").forEach(n ->
                new Thread(n) {
                    @Override
                    public void run() {
                        while (true)
                            pc.consumeV2();
                    }
                }.start()
        );
    }

    public void produceV2() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("P->" + i);
                LOCK.notify();
                isProduced = true;
            }
        }
    }

    public void consumeV2() {
        synchronized (LOCK) {
            if (isProduced) {
                System.out.println("C->" + i);
                LOCK.notify();
                isProduced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Test
    public void testV3() {
        ProduceConsumerTest pc = new ProduceConsumerTest();
        Stream.of("P1", "P2", "P3").forEach(n ->
                new Thread(n) {
                    @Override
                    public void run() {
                        while (true) {
                            pc.produceV3();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start()
        );
        Stream.of("C1", "C2", "C3", "C4").forEach(n ->
                new Thread(n) {
                    @Override
                    public void run() {
                        while (true) {
                            pc.consumeV3();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start()
        );
    }

    public void produceV3() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            i++;
            System.out.println("P->" + i);
            LOCK.notifyAll();
            isProduced = true;

        }
    }

    public void consumeV3() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C->" + i);
            LOCK.notifyAll();
            isProduced = false;
        }
    }
}
