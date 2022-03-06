package com.shawn.touchstone.concurrency.ratelimiter;

import java.util.StringJoiner;

public class TokenBucketTap {
    private long rate;
    private long interval;

    public TokenBucketTap(long rate, long interval) {
        this.rate = rate;
        this.interval = interval;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TokenBucketTap.class.getSimpleName() + "[", "]")
                .add("rate=" + rate)
                .add("interval=" + interval)
                .toString();
    }
}
