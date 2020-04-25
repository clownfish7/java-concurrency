package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-25 17:39
 */
public class ThreadCloseForceTest {

    @Test
    public void test() {
        ThreadService service = new ThreadService();
        long start = System.currentTimeMillis();
        service.execute(() -> {
            //load a very heavy resource.
            /*while (true) {

            }*/
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown(10000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    public class ThreadService {
        private Thread executeThread;
        private boolean finished = false;

        public void execute(Runnable task) {
            executeThread = new Thread(() -> {
                Thread runner = new Thread(task);
                runner.setDaemon(true);
                runner.start();
                try {
                    runner.join();
                    finished = true;
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            });
            executeThread.start();
        }

        public void shutdown(long mills) {
            long currentTime = System.currentTimeMillis();
            while (!finished) {
                if ((System.currentTimeMillis() - currentTime) >= mills) {
                    System.out.println("任务超时，需要结束他!");
                    executeThread.interrupt();
                    break;
                }

                try {
                    executeThread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("执行线程被打断!");
                    break;
                }
            }

            finished = false;
        }
    }
}
