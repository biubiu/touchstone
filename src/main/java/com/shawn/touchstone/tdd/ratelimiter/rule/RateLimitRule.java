package com.shawn.touchstone.tdd.ratelimiter.rule;

import java.util.List;

public interface RateLimitRule {

    void addLimit(String appId, RuleConfig.ApiLimit apiLimit);

    void addLimits(String appId, List<RuleConfig.ApiLimit> limits);

    RuleConfig.ApiLimit getLimit(String apiId, String url);
}
