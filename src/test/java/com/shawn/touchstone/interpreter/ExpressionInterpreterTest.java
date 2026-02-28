package com.shawn.touchstone.interpreter;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionInterpreterTest {

    @Test
    public void interpret() {
        ExpressionInterpreter interpreter = new ExpressionInterpreter();
        Long res = interpreter.interpret("1 + 2 * 3 * 3");
        assertThat(res, is(9));
    }
}