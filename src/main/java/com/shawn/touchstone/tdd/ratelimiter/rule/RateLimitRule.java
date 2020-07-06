package com.shawn.touchstone.tdd.ratelimiter.rule;

public interface RateLimitRule {

    RuleConfig.ApiLimit getLimit(String apiId, String url);
}
