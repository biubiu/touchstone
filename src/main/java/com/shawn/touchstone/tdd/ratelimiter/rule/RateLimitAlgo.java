package com.shawn.touchstone.tdd.ratelimiter.rule;

public interface RateLimitAlgo {

    boolean tryAcquire();

}
