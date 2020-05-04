package com.clownfish7.concurrency.part3.collections;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-05-04 20:29
 */
public class PriorityLinkedListTest {

    @Test
    public void test() {
        PriorityLinkedList<Integer> list = PriorityLinkedList.of(1,3,5,7,9,2,4,6,8);
        assert !list.isEmpty();
        list.addFirst(11);
        while (!list.isEmpty()) {
            System.out.println(list.removeFirst());
        }
    }
}
