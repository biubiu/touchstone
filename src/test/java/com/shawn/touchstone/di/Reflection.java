package com.shawn.touchstone.di;

import org.junit.Test;

public class Reflection {

  @Test
  public void testParse() throws ClassNotFoundException {
    String a = "123";
    Object inta = Integer.parseInt(a);
    System.out.println((Integer)inta);
  }
}
