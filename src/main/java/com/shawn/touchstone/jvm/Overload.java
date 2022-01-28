package com.shawn.touchstone.jvm;

import java.util.Arrays;

public class Overload {
    public static void main(String[] args) {
        invoke(null, 1);
        invoke(null, 1, 2);
        invoke(null, new Object[]{1});
    }

    public static void invoke(Object o1, Object... args) {
        System.out.println("I1 o1 = " + o1 + ", args = " + Arrays.deepToString(args));
    }

    public static void invoke(String s, Object obj, Object... args) {
        System.out.println("I2 s = " + s + ", obj = " + obj + ", args = " + Arrays.deepToString(args));
    }
}
