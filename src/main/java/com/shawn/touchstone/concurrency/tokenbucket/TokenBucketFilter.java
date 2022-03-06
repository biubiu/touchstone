package com.shawn.touchstone.concurrency.tokenbucket;

public interface TokenBucketFilter {

    void getToken() throws InterruptedException;
}
