package com.clownfish7.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author You
 * @create 2020-05-01 15:39
 */
public class StreamTest {

    @Test
    public void test() {
        List<Integer> collect = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(3, 4, 5))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test2() {
        IntStream.range(1,5).forEach(System.out::println);
        IntStream.rangeClosed(1,5).forEach(System.err::println);
    }
}
