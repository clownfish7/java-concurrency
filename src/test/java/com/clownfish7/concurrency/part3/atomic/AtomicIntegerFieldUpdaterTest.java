package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author You
 * @create 2020-05-03 1:19
 */
public class AtomicIntegerFieldUpdaterTest {

    /**
     * 1. 想让类的属性具备原子操作性
     *      1. volatile
     *      2. 非 private/protected 如果是当前类也可以是 private/protected
     *      3. 类型必须一致
     *      4. 其他
     * 2. 不想使用锁
     * 3. 大量需要原子类型修饰的对象相比较耗费内存
     */

    TaskMe taskMe = new TaskMe();

    @Test
    public void test() {
        AtomicIntegerFieldUpdater<TaskMe> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(TaskMe.class, "num");
        assert atomicIntegerFieldUpdater.getAndIncrement(taskMe) == 0 : "error!";
    }

    @Test
    public void test1() {
        AtomicReferenceFieldUpdater<TaskMe, Integer> atomicReferenceFieldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(TaskMe.class, Integer.class, "num");
        assert atomicReferenceFieldUpdater.get(taskMe) == 0 : "error!";
    }

    class TaskMe {
        // java.lang.IllegalArgumentException: Must be volatile type
        volatile int num = 0;
    }
}
