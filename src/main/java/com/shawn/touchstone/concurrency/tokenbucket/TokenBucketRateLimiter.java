package com.shawn.touchstone.concurrency.tokenbucket;

public class TokenBucketRateLimiter {
    private int rate;
    private int intervals;
    private TimeWrapper timeWrapper;
    private long lastRun;
    private double allowance;

    public TokenBucketRateLimiter (TimeWrapper timeWrapper, int rate, int intervals) {
        this.rate = rate;
        this.intervals = intervals;
        this.timeWrapper = timeWrapper;
        this.lastRun = timeWrapper.milli();
        this.allowance = rate;
    }

    public TokenBucketRateLimiter(int rate, int intervals) {
        this(new TimeWrapper(), rate, intervals);
    }

    public boolean throttle() {
        long curr = timeWrapper.milli();
        long timePassed = curr - lastRun;
        lastRun = curr;
        allowance  += timePassed * (rate * 1.0 / intervals);
        if (allowance > rate) {
            allowance = rate;
        }
        if (allowance < 1.0) {
            return true;
        }
        allowance--;
        return false;
    }

}
