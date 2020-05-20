package com.shawn.touchstone.utj2.ch10;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class ContainsMatches  extends TypeSafeMatcher<List<Match>> {

    private Match[] expected;

    public ContainsMatches(Match[] expected) {
        this.expected = expected;
    }
    @Override
    protected boolean matchesSafely(List<Match> actuals) {
        if (actuals.size() != expected.length) return false;
        for (int i = 0; i < expected.length; i++) {
            if (!equals(expected[i], actuals.get(i))) return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("<" + expected.toString() + ">");
    }

    @Factory
    public static <T> Matcher<List<Match>> containsMatches(Match[] expected) {
        return new ContainsMatches(expected);
    }
    private boolean equals(Match expected, Match actual) {
        return expected.searchString.equals(actual.searchString) &&
                expected.surroundingContext.equals(actual.surroundingContext);
    }

}
