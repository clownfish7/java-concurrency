package com.clownfish7.concurrency.part2.producerConsumer;

public class Message {
    private String data;

    public Message(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}