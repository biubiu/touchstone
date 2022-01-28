package com.shawn.touchstone.concurrent.demos;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.lang.Integer.sum;

public class BlockingQueueWithMutex<T> {

    T[] arr;
    int head;
    int tail;
    int capacity;
    int size = 0;
    ReentrantLock lock = new ReentrantLock();

    public BlockingQueueWithMutex(int capacity) {
        arr = (T[]) new Object[capacity];
        this.capacity = capacity;
        List.of(1,2).stream().reduce(0, (a, b) -> a + b);
    }

    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        while (size == capacity) {
            lock.unlock();
            lock.lock();
        }
        if (tail == capacity) {
            tail = 0;
        }
        arr[tail] = item;
        size++;
        tail++;
        lock.unlock();
    }

    public T dequeue() throws InterruptedException {
        lock.lock();
        T item = null;

        while (size == 0) {
            lock.unlock();
            lock.lock();
        }
        if (head == capacity) {
            head = 0;
        }
        item = arr[head];
        arr[head] = null;
        head++;
        size--;
        lock.unlock();
        return item;
    }
}
