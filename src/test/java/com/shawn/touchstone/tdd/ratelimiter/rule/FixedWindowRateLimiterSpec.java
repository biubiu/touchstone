package com.shawn.touchstone.tdd.ratelimiter.rule;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.testing.FakeTicker;
import com.google.common.util.concurrent.FakeTimeLimiter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FixedWindowRateLimiterSpec {

//    static class FakeTicker extends Ticker {
//
//        @Override
//        public long read() {
//            return 0;
//        }
//    }
//
    @Test
    public void testTryAcquire() {
        FakeTicker ticker = new FakeTicker();


        RateLimitAlgo rateLimitAlgo = new FixedWindowRateLimiter(5, Stopwatch.createStarted(ticker));
        //ticker.setAutoIncrementStep(100, TimeUnit.MILLISECONDS)
        //when(ticker.read()).thenReturn()
        ticker.setAutoIncrementStep(100, TimeUnit.MILLISECONDS);

        boolean passed1 = rateLimitAlgo.tryAcquire();
        System.out.println(ticker.read() + "");
        assertTrue(passed1);


        boolean passed2 = rateLimitAlgo.tryAcquire();
        System.out.println(ticker.read() + "");
        assertTrue(passed2);


        boolean passed3 = rateLimitAlgo.tryAcquire();
        System.out.println(ticker.read() + "");
        assertTrue(passed3);


        boolean passed4 = rateLimitAlgo.tryAcquire();
        System.out.println(ticker.read() + "");
        assertTrue(passed4);
        System.out.println(ticker.read() + "");

        boolean passed5 = rateLimitAlgo.tryAcquire();
        System.out.println(ticker.read() + "");
        assertTrue(passed5);


        System.out.println("6 " + ticker.read());
        boolean passed6 = rateLimitAlgo.tryAcquire();
        assertFalse(passed6);

        boolean passed7 = rateLimitAlgo.tryAcquire();
        System.out.println(ticker.read() + "");
        assertFalse(passed7);
    }

//    private Stopwatch stopwatch;
//
//    @Before
//    public void setup() {
//        stopwatch = stopwatch.createStarted();
//    }
//
//    @Test
//    public void whenExceedLimitsThenReturnFalse() {
//        RateLimitAlgo rateLimitAlgo = new FixedWindowRateLimiter(10, stopwatch);
//        simulateSuccessRequest(10, rateLimitAlgo);
//        assertFalse(rateLimitAlgo.tryAcquire());
//    }
//
//    @Test
//    public void whenExceedingOneSecondResetLimit() throws InterruptedException {
//        RateLimitAlgo rateLimitAlgo = new FixedWindowRateLimiter(10, stopwatch);
//        simulateSuccessRequest(10, rateLimitAlgo);
//        assertFalse(rateLimitAlgo.tryAcquire());
//        TimeUnit.MILLISECONDS.sleep(1000);
//        simulateSuccessRequest(10, rateLimitAlgo);
//    }
//
//    private void simulateSuccessRequest(int count, RateLimitAlgo rateLimitAlgo) {
//        for (int i = 0; i < count; i++) {
//            assertTrue(rateLimitAlgo.tryAcquire());
//        }
//    }
//
//    @After
//    public void reset() {
//        stopwatch.reset();
//    }
}
