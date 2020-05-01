package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:15
 */
public class RealResult implements Result {

    private final Object resultValue;

    public RealResult(Object resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public Object getResultValue() {
        return this.resultValue;
    }

}
