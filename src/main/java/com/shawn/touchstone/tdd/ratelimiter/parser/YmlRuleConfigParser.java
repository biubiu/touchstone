package com.shawn.touchstone.tdd.ratelimiter.parser;

import com.shawn.touchstone.tdd.ratelimiter.rule.RuleConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class YmlRuleConfigParser implements RuleConfigParser{

    private Yaml yaml;

    public YmlRuleConfigParser() {
        yaml = new Yaml();
    }

    @Override
    public RuleConfig parse(InputStream inputStream) {
        RuleConfig ruleConfig = yaml.loadAs(inputStream, RuleConfig.class);
        return ruleConfig;
    }
}
