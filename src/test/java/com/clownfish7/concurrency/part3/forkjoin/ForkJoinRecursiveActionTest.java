package com.clownfish7.concurrency.part3.forkjoin;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-03 22:09
 */
public class ForkJoinRecursiveActionTest {

    private final static int MAX_THRESHOLD = 3;
    private final static AtomicInteger SUM = new AtomicInteger();

    @Test
    public void test() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculatedRecursiveAction(0, 10));
        forkJoinPool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(SUM.get());
    }


    class CalculatedRecursiveAction extends RecursiveAction {

        int start;
        int end;

        public CalculatedRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int mid = (start + end) / 2;
                CalculatedRecursiveAction left = new CalculatedRecursiveAction(start, mid);
                CalculatedRecursiveAction right = new CalculatedRecursiveAction(mid + 1, end);
                left.fork();
                right.fork();
            }
        }
    }

}
