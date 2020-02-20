/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.metrics;


import com.shawn.touchstone.metrics.models.RequestInfo;
import com.shawn.touchstone.metrics.models.RequestStat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Aggregator {

    public Map<String, RequestStat> aggregate(Map<String, List<RequestInfo>> requestInfos, Long duration) {
        Map<String, RequestStat> requestStats = new HashMap<>();
        requestInfos.entrySet().forEach( entry -> {
            String apiName = entry.getKey();
            List<RequestInfo> requestInfoPerApi = entry.getValue();
            RequestStat stat = doAggregate(requestInfoPerApi, duration);
            requestStats.put(apiName, stat);
        });
        return requestStats;
    }

    private RequestStat doAggregate(List<RequestInfo> requestInfoPerApi, Long duration) {
        List<Double> respTimes  = requestInfoPerApi.stream().map(resp -> resp.getResponseTime())
                                                            .sorted().collect(toList());
        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(max(respTimes));
        requestStat.setMinResponseTime(min(respTimes));
        requestStat.setAvgResponseTime(avg(respTimes));
        requestStat.setP99ResponseTime(p99(respTimes));
        requestStat.setP999ResponseTime(p999(respTimes));
        requestStat.setTps(tps(respTimes.size(), duration / 1000));
        return requestStat;
    }

    private double p999(List<Double> respTimes) {
        int idx999 = (int)(respTimes.size() * 0.999);
        return respTimes.get(idx999);
    }

    private double p99(List<Double> respTimes) {
        int idx99 = (int)(respTimes.size() * 0.99);
        return respTimes.get(idx99);
    }

    private double min(List<Double> respTimes) {
        return respTimes.get(0);
    }

    private double max(List<Double> respTimes) {
        return respTimes.get(respTimes.size() - 1);
    }

    private double avg(List<Double> respTimes) {
        return respTimes.stream().reduce(0.0, (x, y) -> x + y).doubleValue() / respTimes.size();
    }

    private long tps(int size, long duration) {
        return (long)size/duration;
    }
}



