package com.shawn.touchstone.alg;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;


public class MaximizeCapitalTest {


    @Test
    public void findMaximumCapital() {
        int[] capital = {0, 1, 2, 3};
        int[] profits = {1, 2, 3, 5};
        int initialCapitals = 0;
        int num = 3;
        int result = MaximizeCapital.findMaximumCapital(capital, profits, initialCapitals, num);
        assertThat(result, Is.is(3));
    }
}