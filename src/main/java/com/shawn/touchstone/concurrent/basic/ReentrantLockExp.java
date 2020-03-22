package com.shawn.touchstone.concurrent.basic;

public class ReentrantLockExp {

  public synchronized void doSth() {
    System.out.println("from parent");
  }

  static class Sub extends ReentrantLockExp {

    @Override
    public synchronized void doSth() {
      System.out.println("from sub");
      super.doSth();
    }
  }

  public static void main(String[] args) {
    new Sub().doSth();
  }
}
