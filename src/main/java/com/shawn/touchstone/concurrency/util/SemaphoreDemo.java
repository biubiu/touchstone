package com.shawn.touchstone.concurrency.util;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {


    private static class PrintQueue {
        private final Semaphore semaphore;

        public PrintQueue() {
            semaphore = new Semaphore(3);
        }

        public void printJob() {
            try {
                semaphore.acquire();
                long duration = (long) (Math.random() * 10);
                System.out.printf("%s, PrintQueue: Printing a Job during %d seconds \n",
                        Thread.currentThread().getName(), duration);
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

    private static class Job implements Runnable {
        private PrintQueue printQueue;

        public Job(PrintQueue printQueue) {
            this.printQueue = printQueue;
        }

        @Override
        public void run() {
            System.out.printf("%s: Going to print a job\n",Thread.currentThread().getName());
            printQueue.printJob();
            System.out.printf("%s: The document has been printed\n",Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        for (int i = 0; i < 10; i++) {
            new Thread(new Job(printQueue), "Thread " + i).start();
        }
    }
}
