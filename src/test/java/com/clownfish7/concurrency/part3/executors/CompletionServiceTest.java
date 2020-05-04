package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-04 14:03
 */
public class CompletionServiceTest {

    @Test
    public void testTake() throws ExecutionException, InterruptedException {
        // will block
        ExecutorService service = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(service);
        List<Future<Integer>> futures = new ArrayList<>();
        Future<Integer> future = executorCompletionService.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            return 10;
        });

        Future<Integer> take = executorCompletionService.take();
        System.out.println(take.get());
        System.out.println(future.get());

        // poll method is  not block , maybe null , must be careful!
    }


    @Test
    public void testGetUnsuccessfulTasks() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(service);
        List<Callable<Integer>> taskList = IntStream.range(0, 5).boxed().map(MyTask::new).collect(Collectors.toList());

        taskList.forEach(executorCompletionService::submit);

        Thread.sleep(1000);
        service.shutdownNow();
        taskList.stream().filter(c -> !((MyTask) c).isSuccess()).forEach(System.out::println);


        TimeUnit.SECONDS.sleep(10);
    }

    class MyTask implements Callable<Integer> {

        boolean success = false;
        int i;

        public MyTask(int i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("---   doing   ---" + i);
            Thread.sleep(2000);
            System.out.println("--- finished! ---" + i);
            success = true;
            return 1;
        }

        public boolean isSuccess() {
            return this.success;
        }
    }


}
