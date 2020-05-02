package com.shawn.touchstone.di;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import org.junit.Test;

public class Reflection {

  @Test
  public void testParse() throws ClassNotFoundException {
    String a = "123";
    Object inta = Integer.parseInt(a);
    System.out.println((Integer)inta);
  }

  @Test
  public void testLinkedList() {
    LinkedList<Integer> l = new LinkedList<>();
    l.add(1);
    l.add(2);
    l.add(3);
    assertEquals(3, l.pollLast().intValue());
    assertEquals(2, l.size());
    assertEquals(1, l.pop().intValue());
    assertEquals(1, l.size());
  }
}
