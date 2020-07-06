package com.shawn.touchstone.tdd.ratelimiter.parser;

import com.shawn.touchstone.tdd.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

public interface RuleConfigParser {
    RuleConfig parse(InputStream inputStream);
}
