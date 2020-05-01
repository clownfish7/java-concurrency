package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:54
 */
public class MakeStringClientThread extends Thread {

    private final ActiveObject activeObject;
    private final char fillChar;

    public MakeStringClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; ; i++) {
                Result result = activeObject.makeString(i + 1, fillChar);
                Thread.sleep(200);
                String value = (String) result.getResultValue();
                System.out.println(Thread.currentThread().getName() + " -> " + value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
