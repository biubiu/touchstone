/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.metrics;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.shawn.touchstone.metrics.models.RequestInfo;
import com.shawn.touchstone.metrics.storage.MemMetricsStorage;
import com.shawn.touchstone.metrics.storage.MetricsStorage;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Executors;

public class MetricsCollector {

    private static final int DEFAULT_STORAGE_THREAD_POOL_SIZE = 20;

    private MetricsStorage metricsStorage;

    private EventBus eventBus;

    public MetricsCollector() {
        this(new MemMetricsStorage(), DEFAULT_STORAGE_THREAD_POOL_SIZE);
    }

    public MetricsCollector(MetricsStorage metricsStorage, int threadNumToSaveDate) {
        this.metricsStorage = metricsStorage;
        this.eventBus = new AsyncEventBus(Executors.newFixedThreadPool(threadNumToSaveDate));
        this.eventBus.register(new EventBusChangeRecorder(metricsStorage));
    }

    public void recordRequest(RequestInfo requestInfo) {
        if (requestInfo != null || StringUtils.isBlank(requestInfo.getApiName())) {
            return;
        }
        eventBus.post(requestInfo);
    }

    private static class EventBusChangeRecorder {

        private MetricsStorage metricsStorage;

        public EventBusChangeRecorder(MetricsStorage metricsStorage) {
            this.metricsStorage = metricsStorage;
        }

        @Subscribe
        public void saveRequestInfo(RequestInfo requestInfo) {
            metricsStorage.saveRequestInfo(requestInfo);
        }
    }
}
