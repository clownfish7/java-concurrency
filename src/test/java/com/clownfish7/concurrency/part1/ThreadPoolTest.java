package com.clownfish7.concurrency.part1;

import java.util.LinkedList;

/**
 * @author You
 * @create 2020-04-26 0:19
 */
public class ThreadPoolTest {


}

class SimpleThreadPool {

    private final int size;
    private final static int DEEFAULT_SIZE = 10;

    public SimpleThreadPool() {
        this(DEEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    private void init() {
    }

    private enum TaskState {
        FREE,
        RUNNING,
        BLOCKED,
        DEAD
    }

    private static class WorkerThread extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        private final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

        public WorkerThread(ThreadGroup group, String name) {
            super(group,name);
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
                synchronized (TASK_QUEUE){
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break outer;
                        }
                    }
                    Runnable runnable = TASK_QUEUE.removeFirst();
                    //TODO ThreadPool
                }
            }
        }
    }
}
