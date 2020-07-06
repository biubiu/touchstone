package com.shawn.touchstone.tdd.ratelimiter.rule;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
public class RuleConfig {
    private List<AppRuleConfig> configs;

    @Data
    public static class AppRuleConfig {
        private String appId;
        private List<ApiLimit> limits;
    }

    @Data
    public static class ApiLimit {

        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        private static final int DEFAULT_UNIT = 60;
        private String api;
        private int limit;
        private int unit;

        public int getUnit(){
            return unit == 0 ? DEFAULT_UNIT: unit;
        }

    }
}
