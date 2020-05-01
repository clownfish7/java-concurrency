package com.clownfish7.concurrency.part2.suspension;

public class SuspensionClient {
    public static void main(String[] args) throws InterruptedException {

        final RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "wtf").start();
        ServerThread serverThread = new ServerThread(queue);
        serverThread.start();
        //serverThread.join();

        Thread.sleep(10000);
        serverThread.close();
    }
}
