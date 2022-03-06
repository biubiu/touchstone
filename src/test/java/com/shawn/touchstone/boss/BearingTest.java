package com.shawn.touchstone.boss;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BearingTest {

    @Test(expected=BearingOutOfRangeException.class)
    public void throwsOnNegativeNumber() {
        new Bearing(-1);
    }

    @Test(expected=BearingOutOfRangeException.class)
    public void throwsWhenBearingTooLarge() {
        new Bearing(Bearing.MAX + 1);
    }

    @Test
    public void answersValidBearing() {
        assertThat(new Bearing(Bearing.MAX).value(), equalTo(Bearing.MAX));
    }

    @Test
    public void answersAngleBetweenItAndAnotherBearing() {
        assertThat(new Bearing(15).angleBetween(new Bearing(12)), equalTo(3));
    }

    @Test
    public void angleBetweenIsNegativeWhenThisBearingSmaller() {
        assertThat(new Bearing(12).angleBetween(new Bearing(15)), equalTo(-3));
    }


    public class Bearing {
        public static final int MAX = 359;
        private int value;

        public Bearing(int value) {
            if (value < 0 || value > MAX) throw new BearingOutOfRangeException();
            this.value = value;
        }

        public int value() { return value; }

        public int angleBetween(Bearing bearing) { return value - bearing.value; }


    }

    public class BearingOutOfRangeException extends RuntimeException {
    }


}
