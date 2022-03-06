package com.shawn.touchstone.concurrency.tokenbucket;

import java.util.concurrent.TimeUnit;

public class MultiThreadedTokenBucketFilter implements TokenBucketFilter{
    private long possibleTokens = 0;
    private final int MAX_TOKENS;
    private final int ONE_SECOND = 1000;

    public static MultiThreadedTokenBucketFilter newMultiThreadedTokenBucketFilter(int maxTokens) {
        MultiThreadedTokenBucketFilter multiThreadedTokenBucketFilter = new MultiThreadedTokenBucketFilter(maxTokens);
        multiThreadedTokenBucketFilter.initialize();
        return multiThreadedTokenBucketFilter;
    }

    MultiThreadedTokenBucketFilter(int maxTokens) {
        MAX_TOKENS = maxTokens;
    }

    void initialize() {
        // Never start a thread in a constructor
        Thread t = new Thread(() -> daemonThread());
        t.setDaemon(true);
        t.start();

    }

    private void daemonThread() {
        while (true) {
            synchronized (this) {
                if (possibleTokens < MAX_TOKENS) {
                    possibleTokens++;
                }
                this.notify();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public void getToken() throws InterruptedException {

        synchronized (this) {
            while (possibleTokens == 0) {
                this.wait();
            }
            possibleTokens--;
        }

        System.out.println(
                "Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis() / 1000);
    }

}
