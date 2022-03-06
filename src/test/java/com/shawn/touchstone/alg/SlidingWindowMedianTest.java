package com.shawn.touchstone.alg;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SlidingWindowMedianTest {

    @Test
    public void medianSlidingWindow() {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        assertArrayEquals(new double[]{1.0,-1.0,-1.0,3.0,5.0,6.0},
                new SlidingWindowMedian().medianSlidingWindow(nums, k), 0.01);
        nums = new int[]{1,3,-1,-3,5,3,6};
        assertArrayEquals(new double[]{1.0,-1.0,-1.0,3.0, 5.0},
                new SlidingWindowMedian().medianSlidingWindow(nums, k), 0.01);
        nums = new int[]{1, 2, 3};
        assertArrayEquals(new double[]{2.0}, new SlidingWindowMedian().medianSlidingWindow(nums, k), 0.01);
    }
}