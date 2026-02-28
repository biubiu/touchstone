package com.shawn.touchstone.utj2.ch1;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreCollectionTest {

    @Test
    public void arithmeticMean() {
        ScoreCollection scoreCollection = new ScoreCollection();
        scoreCollection.add(() -> 5);
        scoreCollection.add(() -> 10);

        int mean = scoreCollection.arithmeticMean();
        assertThat(mean, is(7));
        assertThat(2.32 * 3 , closeTo(6.96, 0.0005));
    }

    @Test
    public void thrownIllegalArgWhenAddNull(){
        ScoreCollection scoreCollection = new ScoreCollection();
        assertThrows(IllegalArgumentException.class, () -> scoreCollection.add(null));
    }
}