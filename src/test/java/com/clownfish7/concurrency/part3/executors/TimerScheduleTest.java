package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author You
 * @create 2020-05-04 1:30
 */
public class TimerScheduleTest {

    @Test
    public void test() throws InterruptedException {
        /**
         * 任务未执行结束 到达执行时间不会执行 会等待结束后才执行
         */
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask,1000,1000);
        Thread.currentThread().join();
    }
}
