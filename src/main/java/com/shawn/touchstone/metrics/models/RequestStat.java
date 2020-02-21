/*
 * Copyright (C) 2020 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.metrics.models;

import com.google.common.base.MoreObjects;
import lombok.Data;

import java.util.Objects;

@Data
public class RequestStat {
    private Double maxResponseTime;
    private Double minResponseTime;
    private Double avgResponseTime;
    private Double p999ResponseTime;
    private Double p99ResponseTime;
    private Long count;
    private Long tps;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestStat that = (RequestStat) o;
        return Objects.equals(maxResponseTime, that.maxResponseTime) &&
                Objects.equals(minResponseTime, that.minResponseTime) &&
                Objects.equals(avgResponseTime, that.avgResponseTime) &&
                Objects.equals(p999ResponseTime, that.p999ResponseTime) &&
                Objects.equals(p99ResponseTime, that.p99ResponseTime) &&
                Objects.equals(count, that.count) &&
                Objects.equals(tps, that.tps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxResponseTime, minResponseTime,
                avgResponseTime, p999ResponseTime, p99ResponseTime, count, tps);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxResponseTime", maxResponseTime)
                .add("minResponseTime", minResponseTime)
                .add("avgResponseTime", avgResponseTime)
                .add("p999ResponseTime", p999ResponseTime)
                .add("p99ResponseTime", p99ResponseTime)
                .add("count", count)
                .add("tps", tps)
                .toString();
    }
}
