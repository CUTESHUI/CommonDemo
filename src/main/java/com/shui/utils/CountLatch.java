package com.shui.utils;

public class CountLatch {

    private volatile int count = 0;
    private final Object object = new Object();

    public void countUp() {
        synchronized (object) {
            count += 1;
        }
    }

    public void countDown() {
        synchronized (object) {
            count -= 1;
            if (count <= 0) {
                innerNotifyAll();
            }
        }
    }

    public void await() throws InterruptedException {
        if (count <= 0) {
            return;
        }
        innerWait();
    }

    private void innerWait() throws InterruptedException {
        synchronized (this) {
            this.wait();
        }
    }

    private void innerNotifyAll() {
        synchronized (this) {
            this.notifyAll();
        }
    }
}
