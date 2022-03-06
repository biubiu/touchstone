package com.shawn.touchstone.tdd.ratelimiter;

import com.shawn.touchstone.tdd.ratelimiter.rule.RuleConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class RateLimiterSpec {

    private RateLimiter rateLimiter;

    @Before
    public void setup() {
        rateLimiter = new RateLimiter("rule.yml");
    }

    @Test
    public void whenUrlIsNotListedThenShouldNotFiltered() {
        assertFalse(rateLimiter.limit("123", "api/asd/w"));
    }

    @Test
    public void givenListedUrlWhenExceedingLimitsThenShouldReturnFalse() {
        simulateSuccessRequest(50, "app-1", "/v1/order");
        assertTrue(rateLimiter.limit("app-1", "/v1/order"));
    }

    private void simulateSuccessRequest(int count, String id, String api) {
        for (int i = 0; i < count; i++) {
            assertFalse(rateLimiter.limit(id, api));
        }
    }

    public void assertRules(RuleConfig expected, RuleConfig actual) {
        List<RuleConfig.AppRuleConfig> expectedApps = expected.getConfigs();
        List<RuleConfig.AppRuleConfig> actualApps = actual.getConfigs();
        assertThat(actualApps, hasSize(expectedApps.size()));
        for (int i = 0; i < expectedApps.size(); i++) {
            assertThat(actualApps.get(i).getAppId(), is(expectedApps.get(i).getAppId()));
            assertThat(actualApps.get(i).getLimits(), containsInAnyOrder(expectedApps.get(i).getLimits().toArray(new RuleConfig.ApiLimit[]{})));
        }
    }

    private RuleConfig rule() {
        RuleConfig ruleConfig = new RuleConfig();

        RuleConfig.AppRuleConfig app1 = new RuleConfig.AppRuleConfig();
        RuleConfig.ApiLimit l1 = new RuleConfig.ApiLimit();
        l1.setApi("/v1/user");
        l1.setLimit(100);
        l1.setUnit(60);
        RuleConfig.ApiLimit l2 = new RuleConfig.ApiLimit();
        l2.setApi("/v1/order");
        l2.setLimit(50);
        l2.setUnit(50);
        app1.setAppId("app-1");
        app1.setLimits(of(l1, l2));


        RuleConfig.AppRuleConfig app2 = new RuleConfig.AppRuleConfig();
        RuleConfig.ApiLimit l3 = new RuleConfig.ApiLimit();
        l3.setApi("/v1/user");
        l3.setLimit(50);
        l3.setUnit(60);
        RuleConfig.ApiLimit l4 = new RuleConfig.ApiLimit();
        l4.setApi("/v1/order");
        l4.setLimit(50);
        l4.setUnit(60);
        app2.setAppId("app-2");
        app2.setLimits(of(l3, l4));

        ruleConfig.setConfigs(of(app1, app2));
        return ruleConfig;
    }
}