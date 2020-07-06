package com.shawn.touchstone.tdd.ratelimiter.rule;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class FixedWindowRateLimiter implements RateLimitAlgo {
    private final int limit;
    private Stopwatch stopwatch;

    private AtomicInteger counter = new AtomicInteger();

    private ReentrantLock lock = new ReentrantLock();
    private static final Long TRY_LOCK_TIMEOUT = 200L;

    public FixedWindowRateLimiter(int limit) {
        this(limit, Stopwatch.createStarted());
    }

    @VisibleForTesting
    protected FixedWindowRateLimiter(int limit, Stopwatch stopwatch) {
        this.limit = limit;
        this.stopwatch = stopwatch;
    }

    public boolean tryAcquire() {
        int updatedCount = counter.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }
        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(1)) {
                        counter.set(0);
                        stopwatch.reset();
                    }
                    updatedCount = counter.incrementAndGet();
                    return updatedCount <= limit;
                } finally {
                    lock.unlock();
                }
            } else {
                throw new InternalError("tryAcquire() wait lock too long." + TRY_LOCK_TIMEOUT + "ms");
            }
        } catch (InterruptedException e) {
            throw new InternalError("tryAcquire() is interrupted by lock time-out.", e);
        }
    }
}
