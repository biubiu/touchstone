package com.shawn.touchstone.concurrency.ratelimiter;

import java.time.Clock;

public class TokenBucket {
    private double allowance;
    private Clock clock;
    private long lastRun;

    public TokenBucket(Clock clock) {
        this.clock = clock;
        lastRun = clock.millis();
    }

    public TokenBucket(double rate, Clock clock) {
        this(clock);
        this.allowance = rate;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public boolean throttle(TokenBucketTap tap) {
        long now = clock.millis();
        double timePassed = (now - lastRun);
        this.addToAllowance(timePassed * ((double) tap.getRate() / (double) tap.getInterval()));
        if (this.allowance > tap.getRate()) {
            this.setAllowance(tap.getRate());
        }
        if (this.allowance < 1.0) {
            return true;
        }
        this.subtractFromAllowance(1.0);
        return false;
    }

    private void subtractFromAllowance(double val) {
        allowance -= val;
    }

    private void addToAllowance(double val) {
        allowance += val;
        lastRun = clock.millis();
    }

}
