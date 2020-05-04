package com.clownfish7.concurrency.part3.collections;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-04 21:58
 */
public class SkipLinkedListTest {


    @Test
    public void test() {
        SkipLinkedList<Integer> skipLinkedList = new SkipLinkedList<>();
        Random random = new Random(System.currentTimeMillis());
        IntStream.range(0,10).forEach(i->{
            skipLinkedList.add(random.nextInt(200));
        });
        System.out.println(skipLinkedList.hight());
        skipLinkedList.dump();
        System.out.println(skipLinkedList.contains(44));
        System.out.println(skipLinkedList.get(44));
    }

    @Test
    public void testString() {
        SkipLinkedList<String> skipLinkedList = new SkipLinkedList<>();
        Random random = new Random(System.currentTimeMillis());
        skipLinkedList.add("java");
        skipLinkedList.add("scala");
        skipLinkedList.add("c");
        skipLinkedList.add("c++");
        skipLinkedList.add("python");
        skipLinkedList.add("hadoop");
        skipLinkedList.add("guava");
        System.out.println(skipLinkedList.hight());
        skipLinkedList.dump();
        System.out.println(skipLinkedList.contains("java"));
        System.out.println(skipLinkedList.get("java"));
    }
}
