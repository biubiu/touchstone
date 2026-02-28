/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.interpreter.alert;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlertInterpreterTest {

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
        String rule = "key1 > 100|| key2 < 100 || key3 = 88";
        Map<String, Long> stats = ImmutableMap.of("key2", 121l);
        assertThrows(RuntimeException.class, () -> {
            AlertInterpreter interpreter = new AlertInterpreter(rule);
            interpreter.interpret(stats);
        });
    }
}