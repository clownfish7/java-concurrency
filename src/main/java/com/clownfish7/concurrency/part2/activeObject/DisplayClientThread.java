package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:54
 */
public class DisplayClientThread extends Thread {

    private final ActiveObject activeObject;

    public DisplayClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; ; i++) {
                String text = Thread.currentThread().getName() + " -> " + i;
                activeObject.displayString(text);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
