package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-04-26 0:19
 */
public class ThreadPoolTest {

    @Test
    public void test() throws InterruptedException {
        SimpleThreadPool threadPool = new SimpleThreadPool();
        IntStream.rangeClosed(0, 40).forEach(i -> {
            threadPool.submit(() -> {
                System.out.println("the runnable begining " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("the runnable finished " + i);
            });
        });
        Thread.currentThread().join(10_000);
    }

}

class SimpleThreadPool {

    private final int size;
    private static final int DEEFAULT_SIZE = 10;
    private static volatile int seq = 0;
    private static final String THREAD_PREFIX = "Thread_Pool-";
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>();
    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    public SimpleThreadPool() {
        this(DEEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    private void init() {
        IntStream.range(0, size).forEach(i -> createWorkTask(i + ""));
    }

    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private void createWorkTask(String name) {
        WorkerTask workerTask = new WorkerTask(GROUP, THREAD_PREFIX + name);
        workerTask.start();
        THREAD_QUEUE.add(workerTask);
    }

    private enum TaskState {
        FREE,
        RUNNING,
        BLOCKED,
        DEAD
    }

    private static class WorkerTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }

        @Override
        public void run() {
            outer:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            this.taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break outer;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    this.taskState = TaskState.RUNNING;
                    runnable.run();
                    this.taskState = TaskState.FREE;
                }
            }
        }
    }
}
