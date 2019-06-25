/*
 * Copyright (C) 2019 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone;

import com.shawn.touchstone.reflection.Coding;
import com.shawn.touchstone.reflection.SeniorProgrammer;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class ReflectionTest {

    @Test
    public void testDynamicProxy() {
       int x = 1;
       int y = x;
       x = 2;
        System.out.println(x);
        System.out.println(y);
    }
}
