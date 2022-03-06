package com.shawn.touchstone.alg;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class BrowserHistoryTest {

    private BrowserHistory browserHistory;

    @Before
    public void setUp() throws Exception {
        browserHistory = new BrowserHistory("a");
    }

    @Test
    public void whenBrowseOneReturnOne() {
        assertThat(browserHistory.getHistory(), contains("a"));
        assertThat(browserHistory.getHistory().size(), is(1));
    }

    @Test
    public void shouldReturnHistoryByVisitedOrder() {
        browserHistory.visit("b");
        browserHistory.visit("c");
        browserHistory.visit("d");
        assertThat(browserHistory.back(1), is("c"));
        assertThat(browserHistory.back(1), is("b"));
        assertThat(browserHistory.forward(1), is("c"));

        browserHistory.visit("e");
        assertThat(browserHistory.forward(2), is("e"));
        assertThat(browserHistory.back(2), is("b"));
        assertThat(browserHistory.back(7), is("a"));
    }

    @Test
    public void shouldReturnAtLeastOneWhenStepsGreaterThanHistorySize() {
        browserHistory.visit("b");
        browserHistory.visit("c");
        assertThat(browserHistory.back(2), is("a"));
        assertThat(browserHistory.back(10), is("a"));
    }

    @Test
    public void shouldReturnForwardsByVisitedOrder() {

        browserHistory.visit("b");
        browserHistory.visit("c");
        assertThat(browserHistory.back(1), is("b"));
        assertThat(browserHistory.back(1), is("a"));
        assertThat(browserHistory.forward(1), is("b"));
        assertThat(browserHistory.forward(1), is("c"));
    }
}