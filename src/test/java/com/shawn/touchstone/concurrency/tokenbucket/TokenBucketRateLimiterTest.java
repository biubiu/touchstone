package com.shawn.touchstone.concurrency.tokenbucket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TokenBucketRateLimiterTest {

    FakeTimeWrapper timeWrapper;
    @Before
    public void create() {
        timeWrapper = new FakeTimeWrapper(0L);
    }
    @Test
    public void throttle() {
        var rateLimiter = new TokenBucketRateLimiter(timeWrapper, 2, 1);
        assertFalse(rateLimiter.throttle());
        assertFalse(rateLimiter.throttle());
        assertTrue(rateLimiter.throttle());
    }

    @Test
    public void burst() {
        timeWrapper.set(1L);
        var rateLimiter = new TokenBucketRateLimiter(timeWrapper, 3, 2);
        assertFalse(rateLimiter.throttle());
        timeWrapper.set(3L);
        assertFalse(rateLimiter.throttle());
        assertFalse(rateLimiter.throttle());
        assertFalse(rateLimiter.throttle());
        assertTrue(rateLimiter.throttle());
    }

    @Test
    public void testThrottle() {
        var simpleRateLimiter = new TokenBucketRateLimiter(timeWrapper, 5, 10);
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());

        assertTrue(simpleRateLimiter.throttle());
        timeWrapper.set(1);
        assertTrue(simpleRateLimiter.throttle());
        timeWrapper.set(2);
        assertFalse(simpleRateLimiter.throttle());
        assertTrue(simpleRateLimiter.throttle());
        timeWrapper.set(4);
        assertFalse(simpleRateLimiter.throttle());
        assertTrue(simpleRateLimiter.throttle());

        timeWrapper.set(14);
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());
        assertFalse(simpleRateLimiter.throttle());
        assertTrue(simpleRateLimiter.throttle());
    }

    class FakeTimeWrapper extends TimeWrapper{

        private long curr;

        public FakeTimeWrapper(long curr) {
            this.curr = curr;
        }

        @Override
        public long milli() {
            return curr;
        }

        public void set(long curr) {
            this.curr = curr;
        }
    }
}