package com.shawn.touchstone.tdd.tameofthrone;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SoutheroesSpec {

    private Southeroes southeroes;

    @Before
    public void create() {
        southeroes = new Southeroes();
    }

    @Test
    public void whenSoutheroesCreatedThenKingdomsIsSet() {
        assertThat(southeroes.getKingdoms(), hasSize(5));
    }

    @Test
    public void whenNoMsgReceivedThenReturnEmptyRuler() {
        assertThat(southeroes.getRuler().ruler, isEmptyOrNullString());
    }

    @Test
    public void whenReceivingMalformedMsgThenIgnore() {
        String msg = "\"zmzmzmzaztzozh\"";
        southeroes.receive(msg);
        assertThat(southeroes.getVotes(), is(0));
    }

    @Test
    public void whenReceivingAMsgAndMatchingThenVotesIncrease () {
        String msg = "Ice, \"zmzmzmzaztzozh\"";
        southeroes.receive(msg);
        assertThat(southeroes.getVotes(), is(1));
    }

    @Test
    public void whenReceivingThreeVotesFromDifferenKingdomsThenRulerIsSet() {
        southeroes.receive("Air, \"oaaawaala\"");
        southeroes.receive("Land, \"a1d22n333a4444p\"");
        southeroes.receive("Ice, \"zmzmzmzaztzozh\"");
        assertThat(southeroes.getRuler().ruler, is("Shan"));
        assertThat(southeroes.getRuler().allies, containsInAnyOrder(Kingdom.AIR, Kingdom.ICE, Kingdom.LAND));
    }
}