package com.shawn.touchstone.di.beans;

public class RateLimiter {
    private RedisCounter redisCounter;

    public RateLimiter(RedisCounter redisCounter) {
        this.redisCounter = redisCounter;
    }

    public void test() {
        System.out.println("Hello World");
        System.out.println(redisCounter.toString());
    }
}
