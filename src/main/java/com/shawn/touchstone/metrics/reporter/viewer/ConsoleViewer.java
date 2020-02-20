package com.shawn.touchstone.metrics.reporter.viewer;

import com.google.gson.Gson;
import com.shawn.touchstone.metrics.models.RequestStat;

import java.util.Map;

public class ConsoleViewer implements StatViewer {

    private Gson gson;

    public ConsoleViewer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void output(Map<String, RequestStat> requestStats, long startTime, long endTime) {
        System.out.println("timespan: [" + startTime + ", " + endTime);
        System.out.println(gson.toJson(requestStats));
    }
}
