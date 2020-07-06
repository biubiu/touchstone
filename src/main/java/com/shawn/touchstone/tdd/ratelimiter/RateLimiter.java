package com.shawn.touchstone.tdd.ratelimiter;

import com.shawn.touchstone.tdd.ratelimiter.parser.RuleConfigParser;
import com.shawn.touchstone.tdd.ratelimiter.rule.FixedWindowRateLimiter;
import com.shawn.touchstone.tdd.ratelimiter.rule.RateLimitAlgo;
import com.shawn.touchstone.tdd.ratelimiter.rule.RateLimitRule;
import com.shawn.touchstone.tdd.ratelimiter.rule.RuleConfig;
import com.shawn.touchstone.tdd.ratelimiter.rule.TrieRateLimitRule;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private RateLimitRule rule;

    private ConcurrentHashMap<String, RateLimitAlgo> counters = new ConcurrentHashMap<String, RateLimitAlgo>();

    private FileRuleConfigSource fileRuleConfigSource;

    public RateLimiter(String fileName) {
        fileRuleConfigSource = new FileRuleConfigSource();
        initialise(fileName);
    }

    private void initialise(String fileName) {
        RuleConfig ruleConfig = fileRuleConfigSource.load(fileName);
        this.rule = new TrieRateLimitRule(ruleConfig);
    }


    public boolean limit(String appId, String url) {
        Objects.requireNonNull(rule);
        RuleConfig.ApiLimit limit;
        try {
            limit = rule.getLimit(appId, url);
        } catch (NoSuchElementException e) {
            return false;
        }

        String counter = appId + ":" + limit.getApi();
        RateLimitAlgo rateCounter = counters.computeIfAbsent(counter, k -> new FixedWindowRateLimiter(limit.getLimit()));
        return !rateCounter.tryAcquire();
    }
}
