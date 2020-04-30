package com.shawn.touchstone.cs212.pokergame;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.of;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PokerTest {

    private Poker poker;

    String[] sf = "6C 7C 8C 9C TC".split(" "); //straight flush
    String[] fk = "9D 9H 9S 9C 7D".split(" "); // four of a kind
    String[] fh = "TD TC TH 7C 7D".split(" "); //full house
    String[] tp = "5D 5C 9H 9C 6S".split(" "); // two pairs
    String[] s1 = "AS 2S 3S 4S 5S".split(" "); // A-5 straight
    String[] s2 = "2C 3C 4C 5S 6S".split(" "); //2-6 straight
    String[] ah = "AS 2S 3S 4S 6S".split(" "); //A high
    String[] sh = "2S 3S 4S 6D 7D".split(" "); //7 high

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setup() {
        poker = new Poker();
    }

    @Test
    public void shouldReturnErrorWhenEmptyHands() {
        expected.expect(IllegalArgumentException.class);
        poker.findHand(emptyList());
    }

    @Test
    public void shouldReturnMaxHand() {
        assertThat(poker.findHand(of(s1, s2, ah, sh)), is(s1));

        assertThat(poker.findHand(of(sf, fk, fh)), is(sf));

        assertThat(poker.findHand(of(fk, fh)), is(fk));

        assertThat(poker.findHand(of(fh, fh)), is(fh));

        assertThat(poker.findHand(of(fh)), is(fh));

    }

    @Test
    public void shouldAbleToHandle100Hands() {
        List<String[]> copied = new ArrayList<>();
        IntStream.range(0, 99).forEach(o -> copied.add(fk));
        copied.add(sf);
        assertThat(poker.findHand(copied), is(sf));
    }

    @Test
    public void testHandRank() {
        List s2hand = poker.handRank(s2);

        assertThat(s2hand, is(of(4, 6)));
        List s1hand = poker.handRank(s1);
        assertThat(s1hand, is(of(8, 5)));
    }

    @Test
    public void cardRankShouldReturnInts() {
        List<Integer> ints = poker.cardRank(sf);
        assertThat(ints, is(of(10, 9, 8, 7, 6)));
    }

    @Test
    public void lowerStraightAShouldReturnStraight() {
        List<Integer> ints = poker.cardRank(s1);
        assertThat(ints, is(of(5, 4, 3, 2, 1)));
    }

    @Test
    public void ranksStraight() {
        List<Integer> ranks = of(9, 8, 7, 6, 5);
        assertThat(poker.straight(ranks), is(true));

        ranks = of(9, 8, 8, 7, 6);
        assertThat(poker.straight(ranks), is(false));
    }

    @Test
    public void handsFlush() {
        assertThat(poker.flush(sf), is(true));

        assertThat(poker.flush(fk), is(false));
    }

    @Test
    public void twoPairs() {
        List<Integer> fkRanks = poker.cardRank(fk);
        List<Integer> tpRanks = poker.cardRank(tp);
        assertThat(poker.twoParis(fkRanks), is(emptyList()));
        assertThat(poker.twoParis(tpRanks), is(of(5, 9)));
    }

    @Test
    public void kind() {
        List<Integer> fkRanks = poker.cardRank(fk);
        assertThat(poker.kind(4, fkRanks), is(9));
        assertThat(poker.kind(3, fkRanks), is(nullValue()));
        assertThat(poker.kind(2, fkRanks), is(nullValue()));
    }

}
