package com.clownfish7.concurrency.part3.forkjoin;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-03 22:09
 */
public class ForkJoinRecursiveTaskTest {

    private final static int MAX_THRESHOLD = 3;

    @Test
    public void test() {
        CalculatedRecursiveTask calculatedRecursiveTask = new CalculatedRecursiveTask(1, 10);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> future = forkJoinPool.submit(calculatedRecursiveTask);
        try {
            Integer integer = future.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



    class CalculatedRecursiveTask extends RecursiveTask<Integer> {

        private final int start;
        private final int end;

        public CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= MAX_THRESHOLD) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int mid = (start + end) / 2;
                CalculatedRecursiveTask left = new CalculatedRecursiveTask(start, mid);
                CalculatedRecursiveTask right = new CalculatedRecursiveTask(mid + 1, end);

                left.fork();
                right.fork();

                return left.join() + right.join();
            }
        }
    }

}
