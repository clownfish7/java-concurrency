package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:08
 */

/**
 * 接收异步消息的主动方法
 */
public interface ActiveObject {
    Result makeString(int count, char fillChar);

    void displayString(String text);
}
