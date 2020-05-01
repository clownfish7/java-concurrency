package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:19
 */
public class FutureResult implements Result {

    private Result result;

    private boolean ready = false;

    public synchronized void setResult(Result result) {
        this.result = result;
        this.ready = true;
        this.notifyAll();
    }

    @Override
    public synchronized Object getResultValue() {
        while (!ready) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.result.getResultValue();
    }
}
