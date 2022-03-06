package com.shawn.touchstone.boss;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.junit.Assert.*;

public class RectangleTest {

    static class ConstraintsSidesTo extends TypeSafeMatcher<Rectangle> {

        private int len;

        public ConstraintsSidesTo(int len) {
            this.len = len;
        }

        @Override
        protected boolean matchesSafely(Rectangle rect) {
            return
            Math.abs(rect.origin().x() - rect.opposite().x()) <= len &&
                    Math.abs(rect.origin().y() - rect.opposite().y()) <= len;
        }

        @Factory
        public static <T> Matcher<Rectangle> constrainsSidesTo(int length) {
            return new ConstraintsSidesTo(length);
        }

        @Override
        public void describeTo(Description description) {

        }
    }
}