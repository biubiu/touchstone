package com.shawn.touchstone.tdd.ratelimiter.rule;

import java.util.List;

public interface RateLimitRule {

    void addLimits(String appId, List<RuleConfig.ApiLimit> limits);

    RuleConfig.ApiLimit getLimit(String apiId, String url);
}
