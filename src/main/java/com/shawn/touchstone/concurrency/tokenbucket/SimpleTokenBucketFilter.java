package com.shawn.touchstone.concurrency.tokenbucket;

public class SimpleTokenBucketFilter implements TokenBucketFilter {
    private int MAX_TOKENS;
    private long lastRequestTime = System.currentTimeMillis();
    long possibleToken = 0;

    public SimpleTokenBucketFilter(int maxTokens) {
        this.MAX_TOKENS = maxTokens;
    }

    public synchronized void getToken() throws InterruptedException {
        possibleToken += (System.currentTimeMillis() - lastRequestTime) / 1000;
        if (possibleToken > MAX_TOKENS) {
            possibleToken = MAX_TOKENS;
        }
        if (possibleToken == 0) {
            Thread.sleep(1000);
        }
        else {
            possibleToken--;
        }
        lastRequestTime = System.currentTimeMillis();
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + (System.currentTimeMillis() / 1000));
    }
}
