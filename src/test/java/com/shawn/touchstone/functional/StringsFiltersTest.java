package com.shawn.touchstone.functional;

import org.junit.Test;

public class StringsFiltersTest {

  @Test
  public void iterTest(){
    final String str = "w0098";
    str.chars().forEach(StringsFiltersTest::printChar);
    str.chars().mapToObj(o -> Character.toChars(o)).forEach(System.out::println);
  }

  public static void printChar(int aChar) {
    System.out.println((char)aChar);
  }
}
