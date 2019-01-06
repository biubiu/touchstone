package com.shawn.touchstone.functional;

public class Squint {


  public static void main(String[] args) {
      printSquares(20);
  }

  private static void printSquares(int n) {
    if (n > 0) {
      printSquares(n - 1);
      System.out.format("%d, \t%d\n", n, n*n);
    }
  }
}
