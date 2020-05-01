package com.clownfish7.concurrency.part2.observer;

/**
 * @author You
 * @create 2020-05-01 16:55
 */
public abstract class ObservableRunnable implements Runnable {

    final protected LifeCycleListener listener;

    public ObservableRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    protected void notifyChange(final RunnableEvent event) {
        listener.onEvent(event);
    }

    public enum RunnableState {
        RUNNING, ERROR, DONE
    }

    public static class RunnableEvent {
        private final RunnableState state;
        private final Thread thread;
        private final Throwable cause;

        public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
            this.state = state;
            this.thread = thread;
            this.cause = cause;
        }

        public RunnableState getState() {
            return state;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getCause() {
            return cause;
        }
    }
}
