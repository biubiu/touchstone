package com.shawn.touchstone.tdd.ratelimiter.rule;

import com.google.common.base.Stopwatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FixedWindowRateLimiterSpec {

    private Stopwatch stopwatch;

    @Before
    public void setup() {
        stopwatch = stopwatch.createStarted();
    }

    @Test
    public void whenExceedLimitsThenReturnFalse() {
        RateLimitAlgo rateLimitAlgo = new FixedWindowRateLimiter(10, stopwatch);
        simulateSuccessRequest(10, rateLimitAlgo);
        assertFalse(rateLimitAlgo.tryAcquire());
    }

    @Test
    public void whenExceedingOneSecondResetLimit() throws InterruptedException {
        RateLimitAlgo rateLimitAlgo = new FixedWindowRateLimiter(10, stopwatch);
        simulateSuccessRequest(10, rateLimitAlgo);
        assertFalse(rateLimitAlgo.tryAcquire());
        TimeUnit.MILLISECONDS.sleep(1000);
        simulateSuccessRequest(10, rateLimitAlgo);
    }

    private void simulateSuccessRequest(int count, RateLimitAlgo rateLimitAlgo) {
        for (int i = 0; i < count; i++) {
            assertTrue(rateLimitAlgo.tryAcquire());
        }
    }

    @After
    public void reset() {
        stopwatch.reset();
    }
}
