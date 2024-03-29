package com.shawn.touchstone.concurrency.basic;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceCondition {

  private final AtomicInteger count = new AtomicInteger(0);

  public void service() {
    count.incrementAndGet();
  }
}
