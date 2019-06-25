/*
 * Copyright (C) 2019 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.reflection;

public class SeniorProgrammer implements Coding {

    private static final int CHARGE = 10;

    private int total = 0;

    public int charge() {
        return CHARGE * (total++);
    }

    @Override
    public String coding() {
        return "charging " + charge();
    }
}
