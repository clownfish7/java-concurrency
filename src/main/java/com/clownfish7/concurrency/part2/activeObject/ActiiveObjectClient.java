package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 2:01
 */
public class ActiiveObjectClient {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakeStringClientThread("clownfish1", activeObject).start();
        new MakeStringClientThread("clownfish2", activeObject).start();
        new DisplayClientThread("clownfish3", activeObject).start();
        new DisplayClientThread("clownfish4", activeObject).start();
    }
}
