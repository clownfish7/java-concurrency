package com.clownfish7.concurrency.part3.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author You
 * @create 2020-05-02 19:38
 */
public class AtomicReferenceTest {

    /**
     * cas aba problem
     */
    AtomicReference<Person> atomicReference = new AtomicReference<>();

    @Test
    public void testCreate() {
        Person person = new Person("clownfish7", 18);
        atomicReference = new AtomicReference<>(person);

        assert !atomicReference.compareAndSet(new Person("clownfish7", 18), new Person("monica", 18)) : "error!";
        assert atomicReference.compareAndSet(person, new Person("monica", 18)) : "error!";
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
