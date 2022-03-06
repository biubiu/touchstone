package com.shawn.touchstone.concurrency.ratelimiter;

import java.time.Clock;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {
    private Map<String, TokenBucketTap> rateLimits;
    private Map<String, TokenBucket> tokenBuckets;
    private Clock clock;

    public RateLimiter(Clock clock) {
        this();
        this.clock = clock;
    }

    public RateLimiter() {
        rateLimits = new ConcurrentHashMap<>();
        tokenBuckets = new ConcurrentHashMap<>();
    }

    public void addEntry(String target, long rate, long interval) {
        TokenBucketTap tokenBucketTap = new TokenBucketTap(rate, interval);
        rateLimits.put(target, tokenBucketTap);
    }

    public void rateLimit(final String target) throws RateLimitExceedException {
        Objects.requireNonNull(target);
        for (String targetPattern : getTargetPattern(target)) {
            var tap = rateLimits.get(targetPattern);
            if (tap == null) {
                return;
            }
            var limit = getBucket(targetPattern);
            if (limit == null) {
                limit = new TokenBucket(tap.getRate(), this.clock);
            }

            try {
                if (limit.throttle(tap)) {
                    throw new RateLimitExceedException();
                }
            } finally {
                updateTokenBucket(target, limit);
            }
        }
    }

    private void updateTokenBucket(String target, TokenBucket limit) {
        tokenBuckets.put(target, limit);
    }

    private TokenBucket getBucket(String targetPattern) {
        return tokenBuckets.get(targetPattern);
    }

    private String[] getTargetPattern(String target) {
        return new String[]{target};
    }

    protected class RateLimitExceedException extends Exception {
    }
}
