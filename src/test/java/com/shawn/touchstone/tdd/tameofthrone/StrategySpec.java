package com.shawn.touchstone.tdd.tameofthrone;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class StrategySpec {

    private Strategy strategy;

    @Before
    public void setup() {
        strategy = new Strategy();
    }

    @Test
    public void whenReceiveAMsgIsEmptyThenIgnoreMsg() {
        Kingdom kingdom = Kingdom.LAND;
        assertThat(strategy.matching("", kingdom), is(false));
    }

    @Test
    public void whenReceiveAKingdomIsNullThenIgnore() {
        String msg = "a1d22n333a4444p";
        assertThat(strategy.matching(msg, null), is(false));
    }

    @Test
    public void whenAMsgAndKingdomEmblemMatchingThenReturnTrue() {
        String msg = "a1d22n333a4444p";
        assertThat(strategy.matching(msg, Kingdom.LAND), is(true));
    }

    @Test
    public void whenAMsgAndKingdomEmblemPartialMatchingThenReturnFalse() {
        String msg = "a1d22n333a4444";
        assertThat(strategy.matching(msg, Kingdom.LAND), is(false));
    }

    @Test
    public void whenAMsgMatchingOtherKindomsThenReturnFalse() {
        String msg = "1002Masdhadmotm";
        assertThat(strategy.matching(msg, Kingdom.AIR), is(false));
    }
}