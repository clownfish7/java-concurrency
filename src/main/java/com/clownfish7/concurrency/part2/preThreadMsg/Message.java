package com.clownfish7.concurrency.part2.preThreadMsg;

public class Message {
    private final String value;

    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}