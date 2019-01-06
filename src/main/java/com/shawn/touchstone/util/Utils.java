package com.shawn.touchstone.util;

import java.util.Date;

public class Utils {
  public static void print(Object s) {
    System.out.printf("%s:%s%n", new Date(), s);
  }
}
