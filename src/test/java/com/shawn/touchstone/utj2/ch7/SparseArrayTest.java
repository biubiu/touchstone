package com.shawn.touchstone.utj2.ch7;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class SparseArrayTest {

    private SparseArray sparseArray;

    @BeforeEach
    public void setUp() throws Exception {
        sparseArray = new SparseArray();
    }

    @Test
    public void handlesInsertionInDescedningOrder() {
        sparseArray.put(7, "seven");
        sparseArray.put(6, "six");
        assertThat(sparseArray.get(6), is("six"));
        assertThat(sparseArray.get(7), is("seven"));
    }

    @AfterEach
    public void tearDown() throws Exception {
    }
}