package com.shawn.touchstone.tdd.tameofthrone;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BallotSystemSpec {

    List<Kingdom> nominees;
    BallotSystem system;

    @Before
    public void setup(){
         nominees = Lists.newArrayList(Kingdom.LAND, Kingdom.AIR);
         system = new BallotSystem(nominees);
    }
    @Test
    public void whenBallotSystemCreatedThenNomineesAdded() {
        assertThat(system.getNominees(), is(nominees));
    }

    @Test
    public void whenReceivingFormattedVotesThenVotesIncrease() {
        Ticket ticket = new Ticket(Kingdom.WATER, "A DRAGON IS NOT A SLAVE.", Kingdom.FIRE );
        system.receive(ticket);
        assertThat(system.getVotes(), hasSize(1));
    }

    @Test
    public void whenTicketSenderAndRecipientAreSameThenIgnore() {
        Ticket ticket = new Ticket(Kingdom.FIRE, "A DRAGON IS NOT A SLAVE.", Kingdom.FIRE );
        system.receive(ticket);
        assertThat(system.getVotes(), hasSize(0));
    }

    @Test
    public void whenMultipleTicketsAddToSystemThenTheVotesIncrease() {
        Ticket t1 = new Ticket(Kingdom.FIRE, "A DRAGON IS NOT A SLAVE.", Kingdom.FIRE );
        Ticket t2 = new Ticket(Kingdom.FIRE, "A DRAGON IS NOT A SLAVE.", Kingdom.FIRE );
    }
}
