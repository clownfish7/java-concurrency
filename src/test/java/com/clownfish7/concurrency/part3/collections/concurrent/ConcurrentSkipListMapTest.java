package com.clownfish7.concurrency.part3.collections.concurrent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author You
 * @create 2020-05-05 17:22
 * {@link ConcurrentSkipListMap}
 */
public class ConcurrentSkipListMapTest {

    private ConcurrentSkipListMap<Integer, Object> map = new ConcurrentSkipListMap<>();

    @Test
    public void testPutIfAbsent() {
        map.put(1, 1);
        map.putIfAbsent(2, 1);
        Assertions.assertEquals(map.get(1), 1);
        Assertions.assertEquals(map.get(2), 1);
        map.put(1, 2);
        map.putIfAbsent(2, 2);
        Assertions.assertEquals(map.get(1), 2);
        Assertions.assertEquals(map.get(2), 1);
    }

    @Test
    public void testCompute() {
        map.put(1, 1);
        map.compute(1, (k, v) -> (int) v + 1);
        Assertions.assertEquals(map.get(1), 2);
    }

    @Test
    public void testCeiling() {
        map.put(1, "java");
        map.put(5, "scala");
        map.put(3, "phthon");
        Assertions.assertEquals(map.size(), 3);
        Assertions.assertEquals(map.ceilingKey(2), 3);
    }

    @Test
    public void testFloor() {
        map.put(1, "java");
        map.put(5, "scala");
        map.put(3, "phthon");
        Assertions.assertEquals(map.size(), 3);
        Assertions.assertEquals(map.floorKey(2), 1);
    }

    @Test
    public void testFirst() {
        map.put(1, "java");
        map.put(5, "scala");
        map.put(3, "phthon");
        Assertions.assertEquals(map.size(), 3);
        Assertions.assertEquals(map.firstKey(), 1);
        Assertions.assertEquals(map.firstEntry().getValue(), "java");
    }

    @Test
    public void testLast() {
        map.put(1, "java");
        map.put(5, "scala");
        map.put(3, "phthon");
        Assertions.assertEquals(map.size(), 3);
        Assertions.assertEquals(map.lastKey(), 5);
        Assertions.assertEquals(map.lastEntry().getValue(), "scala");
    }

    @Test
    public void testMerge() {
        map.put(1, "java");
        map.put(5, "scala");
        map.put(3, "phthon");
        Assertions.assertEquals(map.size(), 3);
        Assertions.assertEquals(map.merge(7, "groovy",
                (v1, v2) -> v1.toString().length() > v2.toString().length() ? v1 : v2), "groovy");
        Assertions.assertEquals(map.merge(7, "groovy++",
                (v1, v2) -> v1.toString().length() > v2.toString().length() ? v1 : v2), "groovy++");
    }
}
