package com.clownfish7.concurrency.part3.unsafe;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author You
 * @create 2020-05-03 1:47
 */
public class UnsafeTest {

    @Test
    public void test() {
        // java.lang.SecurityException: Unsafe
        Unsafe unsafe = Unsafe.getUnsafe();
    }

    @Test
    public void tsetGetUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
    }
}
