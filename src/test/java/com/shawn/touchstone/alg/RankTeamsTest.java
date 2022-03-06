package com.shawn.touchstone.alg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RankTeamsTest {

    private RankTeams rankTeams = new RankTeams();
    @Test
    public void rankTeams() {
        var votes = new String[]{"ABC","ACB","ABC","ACB","ACB"};
        assertEquals("ACB", rankTeams.rankTeams(votes));
        votes = new String[]{"WXYZ", "XYZW"};
        assertEquals("XWYZ", rankTeams.rankTeams(votes));
    }
}