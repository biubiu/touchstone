package com.shawn.touchstone.utj2.ch7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SparseArrayTest {

    private SparseArray sparseArray;

    @Before
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

    @After
    public void tearDown() throws Exception {
    }
}