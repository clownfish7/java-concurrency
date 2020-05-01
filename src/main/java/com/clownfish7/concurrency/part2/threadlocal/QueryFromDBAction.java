package com.clownfish7.concurrency.part2.threadlocal;

public class QueryFromDBAction {

    public void execute() {
        try {
            Thread.sleep(1000L);
            String name = "clownfish7 " + Thread.currentThread().getName();
            ActionContext.getActionContext().getContext().setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
