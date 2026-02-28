package com.shawn.touchstone.idgen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class RandomIdGeneratorTest {
    private RandomIdGenerator randomIdGenerator;

    @BeforeEach
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
        assertThrows(IllegalArgumentException.class, () -> randomIdGenerator.getLastSubstrSplittedByDot(null));
        assertThrows(IllegalArgumentException.class, () -> randomIdGenerator.getLastSubstrSplittedByDot(""));
    }

    @Test
    public void testGenerateRandomAlphameric() {
        String acutal = randomIdGenerator.generateRandomAlphameric(6);
        assertNotNull(acutal);
        assertEquals(6, acutal.length());
    }
}