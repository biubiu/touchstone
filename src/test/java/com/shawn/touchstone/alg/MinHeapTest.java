package com.shawn.touchstone.alg;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    public void minHeap() {
        MinHeap minHeap = new MinHeap(Arrays.asList(8, 12, 24, 7, 8, -5, 24, 391, 24, 56, 2, 6, 8, 41));
        minHeap.insert(76);
        assertThat(minHeap.peek(), is(-5));

        assertThat(minHeap.remove(), is(-5));
        assertThat(minHeap.peek(), is(2));
        assertThat(minHeap.remove(), is(2));
        assertThat(minHeap.peek(), is(6));
        minHeap.insert(87);

    }
}