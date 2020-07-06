package com.shawn.touchstone.tdd.ratelimiter;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.shawn.touchstone.tdd.ratelimiter.parser.RuleConfigParser;
import com.shawn.touchstone.tdd.ratelimiter.parser.YmlRuleConfigParser;
import com.shawn.touchstone.tdd.ratelimiter.rule.RuleConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FileRuleConfigSource {

    private static final String YML_EXT = "yml";

    private static final Map<String, RuleConfigParser> PARSER_MAP = ImmutableMap.of(YML_EXT, new YmlRuleConfigParser());

    public RuleConfig load(String fileName) {
        InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream(fileName);
            if (in != null) {
                String ext = Files.getFileExtension(fileName).toLowerCase();
                return PARSER_MAP.get(ext).parse(in);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("cannot find resource name " + fileName);
    }
}
