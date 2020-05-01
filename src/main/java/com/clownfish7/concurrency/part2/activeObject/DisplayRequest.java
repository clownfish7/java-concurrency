package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:24
 */

/**
 * {@link ActiveObject#displayString(String)}
 */
public class DisplayRequest extends MethodRequest {

    private final String text;

    public DisplayRequest(Servant servant, String text) {
        super(servant, null);
        this.text = text;
    }

    @Override
    public void execute() {
        servant.displayString(text);
    }
}
