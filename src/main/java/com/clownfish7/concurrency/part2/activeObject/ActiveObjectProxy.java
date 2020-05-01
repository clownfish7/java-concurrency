package com.clownfish7.concurrency.part2.activeObject;

/**
 * @author You
 * @create 2020-05-02 1:44
 */
class ActiveObjectProxy implements ActiveObject {

    private final Servant servant;
    private final SchedulerThread schedulerThread;

    public ActiveObjectProxy(Servant servant, SchedulerThread schedulerThread) {
        this.servant = servant;
        this.schedulerThread = schedulerThread;
    }

    @Override
    public Result makeString(int count, char fillChar) {
        FutureResult futureResult = new FutureResult();
        schedulerThread.invoke(new MakeStringRequest(servant, futureResult, count, fillChar));
        return futureResult;
    }

    @Override
    public void displayString(String text) {
        schedulerThread.invoke(new DisplayRequest(servant, text));
    }
}
