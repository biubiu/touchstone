package com.shawn.touchstone.concurrency.tokenbucket;

import org.junit.Test;

import java.util.ArrayList;

public class TokenBucketFilterTest {

    @Test
    public void testSimpleBucketFilter() throws InterruptedException {
        max5TokenFor10Requestors(new SimpleTokenBucketFilter(5));
    }

    @Test
    public void testMultiThreadedBucketFilter() throws InterruptedException {
        max5TokenFor10Requestors(MultiThreadedTokenBucketFilter.newMultiThreadedTokenBucketFilter(1));
    }

    public void max5TokenFor10Requestors(TokenBucketFilter tokenBucketFilter) throws InterruptedException {

        var ts = new ArrayList<Thread>(12);
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try{
                    tokenBucketFilter.getToken();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            };
            ts.add(new Thread(runnable, "Thread_" + (i + 1)));
        }

        for (Thread t: ts) {
            t.start();
        }
        for (Thread t : ts) {
            t.join();
        }
    }
}