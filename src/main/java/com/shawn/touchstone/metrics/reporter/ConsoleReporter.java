/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.metrics.reporter;


import com.google.gson.Gson;
import com.shawn.touchstone.metrics.Aggregator;
import com.shawn.touchstone.metrics.storage.MemMetricsStorage;
import com.shawn.touchstone.metrics.storage.MetricsStorage;
import com.shawn.touchstone.metrics.storage.RedisMetricsStorage;
import com.shawn.touchstone.metrics.reporter.viewer.ConsoleViewer;
import com.shawn.touchstone.metrics.reporter.viewer.StatViewer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleReporter extends  ScheduledReporter{

    private ScheduledExecutorService executor;

    private transient  Boolean isRunning = false;

    public ConsoleReporter() {
        this(new MemMetricsStorage(), new Aggregator(), new ConsoleViewer(new Gson()));
    }
    public ConsoleReporter(MetricsStorage metricsStorage, Aggregator aggregator,
                           StatViewer statViewer) {
        super(metricsStorage, aggregator, statViewer);
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        if (isRunning) {
            System.err.println("task already running");
            throw new IllegalStateException("the task is already running");
        }
        isRunning = true;
        executor.scheduleAtFixedRate(() -> {
            long durationInMillis = durationInSeconds * 1000;
            long endTimeInMillis = System.currentTimeMillis();
            long startTimeInMillis = endTimeInMillis - durationInMillis;
            doStatAndReport(startTimeInMillis, endTimeInMillis);
        }, 0, periodInSeconds, TimeUnit.SECONDS);
    }

}
