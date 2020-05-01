package com.clownfish7.concurrency.part2.balking;

public class BalkingClient {
    public static void main(String[] args) {
        BalkingData balkingData = new BalkingData("D:\\balking.txt", "===BEGIN====");
        new CustomerThread(balkingData).start();
        new WaiterThread(balkingData).start();
    }
}