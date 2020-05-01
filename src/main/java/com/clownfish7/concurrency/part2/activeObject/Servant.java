package com.clownfish7.concurrency.part2.activeObject;

import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-02 1:12
 */
class Servant implements ActiveObject {
    @Override
    public Result makeString(int count, char fillChar) {
        char[] chars = new char[count];
        IntStream.range(0, count).forEach(i -> {
            chars[i] = fillChar;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return new RealResult(new String(chars));
    }

    @Override
    public void displayString(String text) {
        System.out.println("display: " + text);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
