package com.shawn.touchstone.alg;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * You have a browser of one tab where you start on the homepage and you can visit another url, get back in the history number of steps or move forward in the history number of steps.
 * <p>
 * Implement the BrowserHistory class:
 * <p>
 * - BrowserHistory(string homepage) Initializes the object with the homepage of the browser.
 * - void visit(string url) Visits url from the current page. It clears up all the forward history.
 * - string back(int steps) Move steps back in history. If you can only return x steps in the history and steps > x,
 * you will return only x steps. Return the current url after moving back in history at most steps.
 * - string forward(int steps) Move steps forward in history. If you can only forward x steps in the history and steps > x,
 * you will forward only x steps. Return the current url after forwarding in history at most steps.
 *
 * a, b, c, d
 * c, b, a
 * b, c
 */

public class BrowserHistory {

    private final Deque<String> history;
    private final Deque<String> forwards;


    public BrowserHistory(String homepage) {
        history = new ArrayDeque<>();
        forwards = new ArrayDeque<>();
        history.push(homepage);
    }

    public void visit(String url) {
        history.addLast(url);
        forwards.clear();
    }

    public String back(int steps) {
        while (steps > 0 && history.size() > 1) {
            forwards.offerLast(history.pollLast());
            steps--;
        }
        return history.peekLast();
    }

    public String forward(int steps) {
        while (steps > 0 && forwards.size() > 0) {
            history.offerLast(forwards.pollLast());
            steps--;
        }
        return history.peekLast();
    }

    @VisibleForTesting
    protected Deque<String> getHistory() {
        return this.history;
    }
}
