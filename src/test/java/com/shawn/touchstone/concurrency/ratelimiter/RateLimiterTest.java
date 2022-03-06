package com.shawn.touchstone.concurrency.ratelimiter;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RateLimiterTest {

    private RateLimiter rateLimiter;
    private Clock mockTime;
    private Instant instant;
    @Before
    public void create() {
        instant = Instant.EPOCH;
        String instant = "2022-02-22T00:00:00Z";
//        mockTime = Clock.fixed(Instant.parse(instant),
//                ZoneOffset.UTC);
        mockTime = mock(Clock.class);
        rateLimiter = new RateLimiter(mockTime);
        when(mockTime.instant()).thenAnswer((invocation) -> instant);
    }

    @Test
    public void rateLimit() {
        rateLimiter.addEntry("abc", 2, 1);
        try {
            rateLimiter.rateLimit("abc");
            rateLimiter.rateLimit("abc");
        } catch (RateLimiter.RateLimitExceedException e) {
            fail();
        }
        assertThrows(RateLimiter.RateLimitExceedException.class, () -> rateLimiter.rateLimit("abc"));
        System.out.println(mockTime.instant().toString());
        instant.plus(1, ChronoUnit.MILLIS);
        System.out.println(mockTime.instant().toString());
        try {
            rateLimiter.rateLimit("abc");
            rateLimiter.rateLimit("abc");
        } catch (RateLimiter.RateLimitExceedException e) {
            fail();
        }
    }
}