package com.shawn.touchstone.metrics.models;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class RequestInfo {
    private String apiName;
    private Double responseTime;
    private Long timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestInfo that = (RequestInfo) o;
        return apiName.equals(that.apiName) &&
                responseTime.equals(that.responseTime) &&
                timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiName, responseTime, timestamp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("apiName", apiName)
                .add("responseTime", responseTime)
                .add("timestamp", timestamp)
                .toString();
    }
}
