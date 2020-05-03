package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-04 2:14
 */
public class RejectedExecutionHandlerTest {

    /**
     * 拒绝策略
     */

    Runnable runnable = () -> {
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    @Test
    public void testAbortPolicy() {
        /**
         * java.util.concurrent.RejectedExecutionException: Task com.clownfish7.concurrency.part3.executors.RejectedExecutionHandlerTest$$Lambda$234/1987083830@2781e022 rejected from java.util.concurrent.ThreadPoolExecutor@57e1b0c[Running, pool size = 2, active threads = 2, queued tasks = 1, completed tasks = 0]
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                r -> {
                    Thread thread = new Thread(r);
                    return thread;
                },
                new ThreadPoolExecutor.AbortPolicy()
        );
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
    }

    @Test
    public void testDiscardPolicy() {
        /**
         * 什么都不干，也没有提示，直接丢弃
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                r -> {
                    Thread thread = new Thread(r);
                    return thread;
                },
                new ThreadPoolExecutor.DiscardPolicy()
        );
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
    }

    @Test
    public void testCallerRunsPolicy() throws InterruptedException {
        /**
         * 谁提交的谁去执行
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                r -> {
                    Thread thread = new Thread(r);
                    return thread;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        TimeUnit.SECONDS.sleep(8);
    }

    @Test
    public void testDiscardOldestPolicy() throws InterruptedException {
        /**
         * queue 中未执行的最先加进去的会被移除然后添加新的这个
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                r -> {
                    Thread thread = new Thread(r);
                    return thread;
                },
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("4");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("5");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.SECONDS.sleep(8);
    }
}
