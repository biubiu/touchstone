package com.shawn.touchstone.alg;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MinMaxStackTest {

    @Test
    public void stackOpsShouldReturnExpected() {
        MinMaxStack stack = new MinMaxStack();
        stack.push(5);
        assertThat(stack.getMin(), is(5));
        assertThat(stack.getMax(), is(5));
        assertThat(stack.peek(), is(5));
        stack.push(7);
        assertThat(stack.getMin(), is(5));
        assertThat(stack.getMax(), is(7));
        assertThat(stack.peek(), is(7));
        stack.push(2);
        assertThat(stack.getMin(), is(2));
        assertThat(stack.getMax(), is(7));
        assertThat(stack.peek(), is(2));
        assertThat(stack.pop(), is(2));
        assertThat(stack.pop(), is(7));
        assertThat(stack.getMin(), is(5));
        assertThat(stack.getMax(), is(5));
        assertThat(stack.peek(), is(5));
    }
}