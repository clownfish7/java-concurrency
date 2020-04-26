package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
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
        IntStream.rangeClosed(0, 100).forEach(i -> {
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
//        TimeUnit.SECONDS.sleep(60);
        threadPool.shutdown();
    }

}

class SimpleThreadPool extends Thread {

    private int size;
    private int min;
    private int max;
    private int active;
    private final int queueSize;
    private static final int DEEFAULT_TASK_QUEUE_SIZE = 200;
    private static volatile int seq = 0;
    private static final String THREAD_PREFIX = "Thread_Pool-";
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private static final List<WorkerTask> THREAD_QUEUE = new ArrayList<>();
    private static final ThreadGroup GROUP = new ThreadGroup("Pool_Group");
    private final DiscardPolicy discardPolicy;
    private volatile boolean destroy = false;

    public SimpleThreadPool() {
        this(4, 8, 12, DEEFAULT_TASK_QUEUE_SIZE, () -> {
            throw new DiscardException("");
        });
    }

    public SimpleThreadPool(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        IntStream.range(0, min).forEach(i -> createWorkTask());
        this.size = min;
        this.start();
    }

    public int getSize() {
        return size;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    public int getQueueSize() {
        return queueSize;
    }

    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n",
                    this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(5_000L);
                if (TASK_QUEUE.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to active.");
                    size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createWorkTask();
                    }
                    System.out.println("The pool incremented to max.");
                    size = max;
                }

                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        System.out.println("=========Reduce========");
                        int releaseSize = size - active;
                        for (Iterator<WorkerTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (releaseSize <= 0)
                                break;

                            WorkerTask task = it.next();
                            task.close();
                            task.interrupt();
                            it.remove();
                            releaseSize--;
                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void submit(Runnable runnable) {
        if (this.destroy) {
            throw new IllegalStateException("the threadPool isDestory");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() >= DEEFAULT_TASK_QUEUE_SIZE) {
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public boolean isDestroy() {
        return this.destroy;
    }

    public void shutdown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            TimeUnit.SECONDS.sleep(10);
        }
        synchronized (THREAD_QUEUE) {
            int size = THREAD_QUEUE.size();
            while (size > 0) {
                for (WorkerTask task : THREAD_QUEUE) {
                    if (task.getTaskState() == TaskState.BLOCKED) {
                        task.interrupt();
                        task.close();
                        size--;
                    } else {
                        TimeUnit.MILLISECONDS.sleep(10);
                    }
                }
            }
        }
        this.destroy = true;
    }

    public static class DiscardException extends RuntimeException {
        public DiscardException(String message) {
            super(message);
        }
    }

    @FunctionalInterface
    public interface DiscardPolicy {
        public void discard() throws DiscardException;
    }

    private void createWorkTask() {
        WorkerTask workerTask = new WorkerTask(GROUP, THREAD_PREFIX + seq++);
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
