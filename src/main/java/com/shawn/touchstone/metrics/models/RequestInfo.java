package com.shawn.touchstone.metrics.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestInfo {
    private String apiName;
    private Double responseTime;
    private long timestamp;


}
