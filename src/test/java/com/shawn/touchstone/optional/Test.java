package com.shawn.touchstone.optional;

import java.util.*;

/**
 * Created by shangfei on 1/8/17.
 */
interface A {
    void foo(Object o); void foo(Integer i); void foo(String s);
}
class B implements A {
    public void foo(Object o) { System.out.println("1");} public void foo(Integer i) { System.out.println("2");} public void foo(String s) { System.out.println("3");}
}
class C extends B {
    public void foo(Object o) { System.out.println("4");} public void foo(Integer i) { System.out.println("5");} public void foo(String s) { System.out.println("6");}
}
class Test {
    public static void main(String[] args) {
        B b = new B();
        Object i = new Integer(100);
        b.foo(i);
        b = new C();
        b.foo(i);

        System.out.println(blackjack(19, 21));
        System.out.println(blackjack(21, 19));
        System.out.println(blackjack(19, 22));
    }

    public static int blackjack(int a, int b){
        int THRESHOLD = 21;
        if (a > THRESHOLD && b > THRESHOLD) {
            return 0;
        }
        if(a > THRESHOLD) {
            return b;
        }
        if(b > THRESHOLD) {
            return a;
        }
        int offsetA = THRESHOLD - a;
        int offsetB = THRESHOLD - b;
        return offsetA < offsetB ? a: b;
    }

    public static void reverse(List<?> list){
        List invertedList = new ArrayList();
        for (int i = list.size() - 1; i >= 0; i--) {
            invertedList.add(list.get(i));
        }
        list = invertedList;
    }
}


