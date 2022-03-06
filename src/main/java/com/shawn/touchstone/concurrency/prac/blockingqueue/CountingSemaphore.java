package com.shawn.touchstone.concurrency.prac.blockingqueue;

public class CountingSemaphore {

    int usedPermits = 0;
    int maxCount;

    public CountingSemaphore(int usedPermits) {
        this.usedPermits = usedPermits;
    }

    public CountingSemaphore(int count, int initialPermits) {
        this.maxCount = count;
        this.usedPermits = this.maxCount - initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (usedPermits == maxCount)
            wait();
        notify();
        usedPermits++;
    }

    public synchronized  void release() throws InterruptedException {
        while (usedPermits == 0) wait();
        usedPermits--;
        notify();
    }
}
