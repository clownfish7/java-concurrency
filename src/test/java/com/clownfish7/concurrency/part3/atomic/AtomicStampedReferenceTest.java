package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author You
 * @create 2020-05-02 19:38
 */
public class AtomicStampedReferenceTest {

    /**
     * cas aba problem
     */
    AtomicStampedReference<Person> atomicStampedReference;

    @Test
    public void testCreate() throws InterruptedException {
        Person person = new Person("clownfish7", 18);
        atomicStampedReference = new AtomicStampedReference<>(person, 0);

        Thread t1 = new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
            boolean result = atomicStampedReference.compareAndSet(person, new Person("matasha", 18), stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + ":" + result);
        });

        Thread t2 = new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            Person matasha = new Person("matasha", 18);
            boolean result = atomicStampedReference.compareAndSet(person, matasha, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + ":" + result);
            stamp = atomicStampedReference.getStamp();
            result = atomicStampedReference.compareAndSet(matasha, new Person("clownfish7", 18), stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + ":" + result);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    class Person {
        String name;
        Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}
