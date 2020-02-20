package com.shawn.touchstone.metrics.reporter.viewer;

import com.shawn.touchstone.metrics.models.RequestStat;

import java.util.Map;

public interface StatViewer {

    void output(Map<String, RequestStat> requestStats, long startTime, long endTime);

    default void addRecipients(String email) {
        throw new UnsupportedOperationException();
    }
}
