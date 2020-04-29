/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.interpreter.alert;

import com.google.common.collect.ImmutableMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlertInterpreterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void interpretShouldRetTrue() {
        String rule = "key1 > 100 && key2 < 30 || key3 < 100 || key4 == 88";
        AlertInterpreter interpreter = new AlertInterpreter(rule);
        Map<String, Long> stats = ImmutableMap.of("key1", 101l, "key2", 121l,
                "key3", 101l, "key4", 88l);

        boolean alert = interpreter.interpret(stats);
        assertThat(alert, is(true));
    }

    @Test
    public void interpretShouldThrowRuntimeExp() {
        thrown.expect(RuntimeException.class);

        String rule = "key1 > 100|| key2 < 100 || key3 = 88";
        AlertInterpreter interpreter = new AlertInterpreter(rule);
        Map<String, Long> stats = ImmutableMap.of("key2", 121l);
        interpreter.interpret(stats);
    }
}