/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.metrics;

import com.shawn.touchstone.metrics.models.RequestInfo;

import java.util.List;
import java.util.Map;

public interface MetricsStorage {
    void saveRequestInfo(RequestInfo requestInfo);

    List<RequestInfo> getRequestInfos(String apiName, long startTime, long endTime);

    Map<String, List<RequestInfo>> getRequestInfos(long startTime, long endTime);
}


