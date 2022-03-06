package com.shawn.touchstone.concurrency.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

    private static class Conference implements Runnable {
        private final CountDownLatch controller;
        private final CountDownLatch startLatch;

        public Conference(int number, CountDownLatch startLatch) {
            this.startLatch = startLatch;
            controller = new CountDownLatch(number);
        }
        public void arrive(String name) {
            System.out.printf("%s has arrived \n", name);
            controller.countDown();
            System.out.printf("Conference: waiting for %d participants \n", controller.getCount());
        }

        @Override
        public void run() {
            System.out.printf("Conference initializing: %d participants \n", controller.getCount());
            try {
                controller.await();
                System.out.printf("All participants has arrived \n");
                startLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
    private static class Participant implements Runnable{
        private Conference conference;
        private String name;

        public Participant(Conference conference, String name) {
            this.conference = conference;
            this.name = name;
        }

        @Override
        public void run() {
            long duration  = (long) (Math.random() * 10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            conference.arrive(name);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        CountDownLatch start = new CountDownLatch(1);
         Conference conference = new Conference(count, start);
         Thread tCon = new Thread(conference);
         tCon.start();

        for (int i = 0; i < count; i++) {
            String tName = "participant " + i;
            new Thread(new Participant(conference, tName), tName).start();
        }
        start.await();
        System.out.printf("conference is ready");
    }
}
