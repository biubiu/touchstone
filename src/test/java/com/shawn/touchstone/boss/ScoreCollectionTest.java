package com.shawn.touchstone.boss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

public class ScoreCollectionTest {

    private ScoreCollection collection;

    @Before
    public void create() {
        collection = new ScoreCollection();
    }

    @Test
    public void throwsExceptionWhenAddingNull() {
        assertThrows(IllegalArgumentException.class, () -> collection.add(null));
    }

    @Test
    public void returnZeroWhenNoElementsAdded() {
        assertThat(collection.arithmeticMean(), is(equalTo(0)));
    }

    @Test
    public void answerArithmeticOfTwoNumbers() {
        collection.add(() -> 5);
        collection.add(() -> 7);

        int actualResult = collection.arithmeticMean();

        assertThat(actualResult, equalTo(6));
    }
}