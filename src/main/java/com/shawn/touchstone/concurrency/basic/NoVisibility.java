package com.shawn.touchstone.concurrency.basic;

import java.util.stream.IntStream;

public class NoVisibility {
  private static boolean ready;
  private static int number;

  private static class ReaderThread extends Thread {

    @Override
    public void run() {
      while(!ready) {
        Thread.yield();
      }
      System.out.println(number);
    }
  }

  public static void main(String[] args) {
    IntStream.range(1, 10).forEach(o -> {
          Thread t = new ReaderThread();
          t.setName(o + "");
          t.start();
    });
    number = 42;
    ready = true;
  }
}
