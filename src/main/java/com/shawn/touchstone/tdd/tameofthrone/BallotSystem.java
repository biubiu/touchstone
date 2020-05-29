package com.shawn.touchstone.tdd.tameofthrone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BallotSystem {

    List<Kingdom> nominees;

    private List<Kingdom> votes;

    private Strategy strategy;

    public BallotSystem(List<Kingdom> nominees) {
        this.nominees = nominees;
        votes = new ArrayList<>();
        strategy = new Strategy();
    }

    public List<Kingdom> getNominees() {
        return nominees;
    }

    public void receive(Ticket ticket) {
        Objects.requireNonNull(ticket);
        if (ticket.getSender() == ticket.getRecipient()) {
            return;
        }
        votes.add(ticket.getRecipient());
    }

    public List<Kingdom> getVotes() {
        return votes;
    }
}
