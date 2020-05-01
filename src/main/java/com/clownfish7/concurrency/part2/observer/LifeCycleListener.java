package com.clownfish7.concurrency.part2.observer;

public interface LifeCycleListener {
    
    void onEvent(ObservableRunnable.RunnableEvent event);
}
