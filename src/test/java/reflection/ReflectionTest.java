/*
 * Copyright (C) 2019 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package reflection;

import org.junit.Test;

import javax.xml.validation.Schema;

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
