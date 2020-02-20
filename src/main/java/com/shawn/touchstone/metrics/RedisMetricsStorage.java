package com.shawn.touchstone.metrics;

import com.shawn.touchstone.metrics.models.RequestInfo;

import java.util.List;
import java.util.Map;

public class RedisMetricsStorage implements MetricsStorage {

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {
        //...
    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {
        throw new UnsupportedOperationException();
    }
}
