package com.shawn.touchstone.alg;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NextIntervalTest {

    private NextInterval nextInterval;

    @Before
    public void create() {
        nextInterval = new NextInterval();
    }

    @Test
    public void testNextIntervals() {
        Interval[] intervals = {new Interval(2,3 ), new Interval(3, 4), new Interval(5, 6)};
        assertThat(nextInterval.nextInterval(intervals), is(new int[]{1, 2, -1}));
        intervals = new Interval[]{new Interval(3, 4), new Interval(1, 5), new Interval(4, 6)};
        assertThat(nextInterval.nextInterval(intervals), is(new int[]{2, -1, -1}));

    }

    @Test
    public void twoIntervalsHasSameNextInterval() {
        Interval[] intervals = new Interval[]{new Interval(3, 4), new Interval(1, 5), new Interval(4, 6), new Interval(1, 4)};
        assertThat(nextInterval.nextInterval(intervals), is(new int[]{2, -1, -1, 2}));
    }
}