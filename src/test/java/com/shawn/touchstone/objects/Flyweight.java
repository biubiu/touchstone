package com.shawn.touchstone.objects;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Flyweight {

    @Test
    public void testInt() {
        Integer a = 56;
        Integer b = 56;
        Integer c = 129;
        Integer d = 129;
        assertTrue(a == b);
        assertTrue(c == d);
    }

    @Test
    public void testStr() {
        String a = "应用";
        String b = "应用";
        String c = new String("应用");
        assertTrue(a == b);
        assertTrue(b == c);
    }
}
