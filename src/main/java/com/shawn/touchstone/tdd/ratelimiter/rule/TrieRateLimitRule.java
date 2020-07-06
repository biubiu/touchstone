package com.shawn.touchstone.tdd.ratelimiter.rule;

import java.util.NoSuchElementException;

public class TrieRateLimitRule implements RateLimitRule {

    private RuleConfig ruleConfig;

    public TrieRateLimitRule(RuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
    }

    public RuleConfig.ApiLimit getLimit(String apiId, String url) {
        return ruleConfig.getConfigs().stream().filter(o -> o.getAppId().equals(apiId)).findAny().
                orElseThrow(() -> new NoSuchElementException()).
                getLimits().stream().
                filter(u -> u.getApi().equals(url)).findAny().
                orElseThrow(() -> new NoSuchElementException());
    }
}
