package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:10
 */

/**
 * 对应 ActiveObject 的每一个方法
 */
public abstract class MethodRequest {

    protected Servant servant;

    protected FutureResult futureResult;

    public MethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();
}
