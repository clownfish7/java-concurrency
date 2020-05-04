package com.clownfish7.concurrency.part3.collections.custom;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-05-04 20:29
 */
public class LinkedListTest {

    @Test
    public void test() {
        LinkedList<String> list = LinkedList.of("python", "scala", "java", "c++");
        assert !list.isEmpty();
        assert list.size() == 4;
        list.addFirst("nodejs");
        while (!list.isEmpty()) {
            System.out.println(list.removeFirst());
        }
    }
}
