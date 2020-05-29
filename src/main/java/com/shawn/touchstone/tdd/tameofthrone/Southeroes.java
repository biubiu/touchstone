package com.shawn.touchstone.tdd.tameofthrone;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Southeroes {

    private List<Kingdom> kingdoms;

    private Strategy strategy;

    private Ruler ruler;

    private List<Kingdom> votes;

    public Southeroes() {
        kingdoms = Lists.newArrayList(Kingdom.values());
        strategy = new Strategy();
        votes = new ArrayList<>();
        ruler = new Ruler();
    }

    public List<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public Ruler getRuler() {
        return ruler;
    }

    public void receive(String msg) {
        if (!isValid(msg)) {
            return;
        }
        String[] raw = msg.split(",");
        Kingdom kingdom = Kingdom.valueOf(raw[0].toUpperCase());
        if (strategy.matching(raw[1], kingdom)) {
            votes.add(kingdom);
        }
        if (hasWinner()) {
            ruler.ruler = "Shan";
            ruler.allies = Sets.newHashSet(votes);
        }
    }

    private boolean isValid(String msg) {
        if (msg == null || msg.isEmpty()) return false;
        String[] raw = msg.trim().split(",");
        if (raw.length != 2) {
            return false;
        }
        return true;
    }

    private boolean hasWinner() {
        return votes.stream().distinct().count() >= 3l;
    }

    public int getVotes() {
        return votes.size();
    }


    public static class Ruler {
        String ruler;
        Set<Kingdom> allies;
    }
}
