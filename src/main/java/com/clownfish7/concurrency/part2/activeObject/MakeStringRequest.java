package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:23
 */

/**
 * {@link ActiveObject#makeString(int, char)}
 */
public class MakeStringRequest extends MethodRequest {

    private final int count;
    private final char fillChar;

    public MakeStringRequest(Servant servant, FutureResult futureResult, int count, char fillChar) {
        super(servant, futureResult);
        this.count = count;
        this.fillChar = fillChar;
    }

    @Override
    public void execute() {
        Result result = servant.makeString(count, fillChar);
        futureResult.setResult(result);
    }
}
