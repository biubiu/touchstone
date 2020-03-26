package com.shawn.touchstone.idgen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RandomIdGeneratorTest {
    private RandomIdGenerator randomIdGenerator;

    @Before
    public void setUp() {
        randomIdGenerator = new RandomIdGenerator();
    }

    @Test
    public void testGetLastSubstrSplittedByDot() {
        String actual = randomIdGenerator.getLastSubstrSplittedByDot("field1.field2.field3");
        assertEquals("field3", actual);

        actual = randomIdGenerator.getLastSubstrSplittedByDot("field1");
        assertEquals("field1", actual);

        actual = randomIdGenerator.getLastSubstrSplittedByDot("field1#field2$field3");
        assertEquals("field1#field2$field3", actual);
    }

    @Test
    public void testGetLastSubstrSplittedByDotNullOrEmpty() {
        String actual = randomIdGenerator.getLastSubstrSplittedByDot(null);
        assertNull(actual);

        actual = randomIdGenerator.getLastSubstrSplittedByDot("");
        assertEquals("", actual);
    }

    @Test
    public void testGenerateRandomAlphameric() {
        String acutal = randomIdGenerator.generateRandomAlphameric(6);
        assertNotNull(acutal);
        assertEquals(6, acutal.length());
    }
}