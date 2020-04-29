package com.shawn.touchstone.interpreter;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ExpressionInterpreterTest {

    @Test
    public void interpret() {
        ExpressionInterpreter interpreter = new ExpressionInterpreter();
        Long res = interpreter.interpret("1 + 2 * 3 * 3");
        assertThat(res, is(9));
    }
}