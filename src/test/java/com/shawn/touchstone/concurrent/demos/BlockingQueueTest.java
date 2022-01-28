package com.shawn.touchstone.concurrent.demos;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BlockingQueueTest {

    BlockingQueueWithMutex<Integer> bq;

    @Before
    public void setup() {
        bq = new BlockingQueueWithMutex<>(5);
    }

    @Test
    public void bqEnqueue() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    bq.enqueue(i);
                    System.out.println("t1 enqueue " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                try {
                    System.out.println("Thread 2 dequeued: " + bq.dequeue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                try {
                    System.out.println("Thread 3 dequeued: " + bq.dequeue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        TimeUnit.MILLISECONDS.sleep(4);
        t2.start();
        t2.join();
        t3.start();
        t1.join();
        t3.join();
    }
}