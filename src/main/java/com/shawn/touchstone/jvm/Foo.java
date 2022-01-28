package com.shawn.touchstone.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Foo {
    private boolean flag = true;


    public boolean getFlag() {
        return this.flag;
    }

    public static void main(String[] args) throws NoSuchFieldException, InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, InstantiationException {
        Foo foo = new Foo();
        Field field = foo.getClass().getDeclaredField("flag");
        Unsafe unsafe = foo.getUnsafeByConstructor();
        long addr = unsafe.objectFieldOffset(field);
        for (byte b = 0; b < 4; b++) {
            System.out.println("Unsafe.putByte: " + b);
            unsafe.putByte(foo, addr, b);

            System.out.println("Unsafe.getByte: " + unsafe.getByte(foo, addr)); // 总是会打印出put的值
            System.out.println("Unsafe.getBoolean: " + unsafe.getBoolean(foo, addr)); // 打印出的值，像是ifeq, flag != 0即true

            System.out.println("foo.flag: " + foo.flag);  // 像是ifeq, flag != 0即true
            System.out.println("foo.getFlag: " + foo.getFlag()); // 像是 ((flag) & 1)

            // 此处是内联了？
            if (foo.flag) {
                System.out.println("Hello, Java!" + " foo.flag");
            }

            // 此处是内联了？
            if (true == foo.flag) {
                System.out.println("Hello, JVM!" + " foo.flag");
            }

            if (foo.getFlag()) {
                System.out.println("Hello, Java!" + " getFlag");
            }

            if (true == foo.getFlag()) {
                System.out.println("Hello, JVM!" + " getFlag");
            }

            System.out.println("-----------------------------------------------------------");
        }
    }

    private static Unsafe getUnsafeByConstructor() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
        unsafeConstructor.setAccessible(true);
        Unsafe unsafe = unsafeConstructor.newInstance();

        return unsafe;
    }
}
