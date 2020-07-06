package com.shawn.touchstone.tdd.ratelimiter;

import com.shawn.touchstone.tdd.ratelimiter.rule.FixedWindowRateLimiter;
import com.shawn.touchstone.tdd.ratelimiter.rule.RateLimitAlgo;
import com.shawn.touchstone.tdd.ratelimiter.rule.RateLimitRule;
import com.shawn.touchstone.tdd.ratelimiter.rule.RuleConfig;
import com.shawn.touchstone.tdd.ratelimiter.rule.TrieRateLimitRule;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private RateLimitRule rule;

    private ConcurrentHashMap<String, RateLimitAlgo> counters = new ConcurrentHashMap<String, RateLimitAlgo>();

    private FileRuleConfigSource fileRuleConfigSource;

    public RateLimiter(String fileName) {
        fileRuleConfigSource = new FileRuleConfigSource();
        this.rule = new TrieRateLimitRule();
        initialise(fileName);
    }

    private void initialise(String fileName) {
        RuleConfig ruleConfig = fileRuleConfigSource.load(fileName);
        ruleConfig.getConfigs().forEach(con -> rule.addLimits(con.getAppId(), con.getLimits()));
    }

    public boolean limit(String appId, String url) {
        Objects.requireNonNull(rule);
        RuleConfig.ApiLimit limit = rule.getLimit(appId, url);
        if (limit == null) {
            return false;
        }
        String counter = appId + ":" + limit.getApi();
        RateLimitAlgo rateCounter = counters.computeIfAbsent(counter, k -> new FixedWindowRateLimiter(limit.getLimit()));
        return !rateCounter.tryAcquire();
    }
}
