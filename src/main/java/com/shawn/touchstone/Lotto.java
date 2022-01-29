package com.shawn.touchstone;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lotto {

  public static void main(String[] args) {

    for (int i = 0; i < 12; i++) {
      System.out.println(gen());
    }
  }

  private static String gen() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    List<Integer> blacks = blacks();
    for (int i = 0; i < 7; i++) {
      int idx = random.nextInt(blacks.size());
      Integer curr = blacks.remove(idx);
      sb.append(" ").append(curr);
    }
    sb.append(" ").append(random.nextInt(20) + 1);
    return sb.toString();
  }

  private static List<Integer> blacks() {
    return IntStream.range(1, 35).boxed().collect(Collectors.toList());
  }

}
