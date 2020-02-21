package com.shawn.touchstone.metrics.storage;

import com.shawn.touchstone.metrics.models.RequestInfo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class MemMetricsStorage implements MetricsStorage {

    private ConcurrentSkipListMap<Long, RequestInfo> timestampSkipListMap;
    private Map<String, ConcurrentSkipListMap<Long, RequestInfo>> apiRequestMap;

    public MemMetricsStorage() {
        this.timestampSkipListMap = new ConcurrentSkipListMap(Comparator.comparingLong(RequestInfo::getTimestamp));
        this.apiRequestMap = new HashMap<>();
    }

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {
        timestampSkipListMap.put(requestInfo.getTimestamp(), requestInfo);
        apiRequestMap.computeIfAbsent(requestInfo.getApiName(),
                k -> new ConcurrentSkipListMap(Comparator.comparingLong(RequestInfo::getTimestamp)))
                .put(requestInfo.getTimestamp(), requestInfo);
    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTime, long endTime) {
        ConcurrentSkipListMap<Long, RequestInfo> map = apiRequestMap.get(apiName);
        return map.subMap(startTime, true, endTime, true).values().stream().collect(toList());
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTime, long endTime) {
        ConcurrentNavigableMap<Long, RequestInfo> timeResult = timestampSkipListMap.subMap(startTime, true, endTime, true);
        return timeResult.entrySet().stream().
                collect(groupingBy(o -> o.getValue().getApiName(),
                        mapping(o -> o.getValue(), toList())));
    }
}
