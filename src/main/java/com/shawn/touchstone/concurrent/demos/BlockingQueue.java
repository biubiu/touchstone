package com.shawn.touchstone.concurrent.demos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {
    T[] arr;
    int head;
    int tail;
    int capacity;
    int size = 0;
    Object lock = new Object();
    public BlockingQueue(int capacity) {
        arr = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            while (size == capacity) {
                lock.wait();
            }
            if (tail == capacity) {
                tail = 0;
            }
            arr[tail] = item;
            size++;
            tail++;
            lock.notifyAll();
        }
    }

    public T dequeue() throws InterruptedException {

        T item = null;
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
            }
            if (head == capacity) {
                head = 0;
            }
            item = arr[head];
            arr[head] = null;
            head++;
            size--;
            lock.notifyAll();
        }

        return item;
    }
}
