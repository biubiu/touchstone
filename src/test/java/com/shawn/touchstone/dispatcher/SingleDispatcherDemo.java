/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.dispatcher;

public class SingleDispatcherDemo {

    static class ParentClass {
        void f() {
            System.out.println(" I am parent class's f()");
        }
    }

    static class ChildClass extends ParentClass {
        void f() {
            System.out.println("I am child class's f()");
        }
    }

    static class SingleDispatcher {

        void polymorphismFunction(ParentClass p) {
            p.f();
        }

        void overloadFunction(ParentClass p) {
            System.out.println("I'm overloadFunction(Parent p). ");
            p.f();
        }

        void overloadFunction(ChildClass c) {
            System.out.println("I'm overloadFunction(ChildClass c).");
            c.f();
        }
    }


    public static void main(String[] args) {
        SingleDispatcher singleDispatcher = new SingleDispatcher();
        ParentClass parentClass = new ChildClass();
        singleDispatcher.polymorphismFunction(parentClass);//invocation of which object's method determined by the actual type of passed parameter
        singleDispatcher.overloadFunction(parentClass);//invocation of which method of the object determined by the declared type of passed parameter
    }
}
