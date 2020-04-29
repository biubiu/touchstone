package com.shawn.touchstone.cs212.pokergame;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.of;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Poker {

    public String[] findHand(List<String[]> hands) {
        hands.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                List handRank1 = handRank(o1);
                List handRank2 = handRank(o2);
                return 0;
            }
        });
        return hands.get(0);
    }

    public List handRank(String[] hands) {
        List<Integer> ranks = cardRank(hands);
        Integer max = ranks.get(0);
        Boolean isStraight = straight(ranks);
        Boolean isFlush = flush(hands);
        LinkedHashMap<Integer, Integer> occurrences = grouping(ranks);
        List<Integer> descCounts = new ArrayList<>(occurrences.values());
        List<Integer> ranksByCounts = new ArrayList<>(occurrences.keySet());
        List<Integer> twoParisIndex = IntStream.range(0, descCounts.size()).boxed()
                .filter(in -> descCounts.get(in).equals(2)).collect(toList());
        if (isStraight && isFlush) {
            return of(8, max);
        } else if (descCounts.contains(4)) {
            return of(7, ranksByCounts.get(descCounts.indexOf(4)), ranksByCounts.get(descCounts.indexOf(1)));
        } else if (descCounts.contains(3) && descCounts.contains(2)) {
            return of(6, ranksByCounts.get(descCounts.indexOf(3)), ranksByCounts.get(descCounts.indexOf(2)));
        } else if (isFlush) {
            return of(5, ranks);
        } else if (isStraight) {
            return of(4, max);
        } else if (descCounts.contains(3)) {
            return of(3, descCounts.get(descCounts.indexOf(3)), ranks);
        } else if (twoParisIndex.size() == 2) {
            return of(2, of(ranksByCounts.get(twoParisIndex.get(0)), ranksByCounts.get(twoParisIndex.get(1))), ranks);
        } else if (twoParisIndex.size() == 1) {
            return of(1, of(ranksByCounts.get(twoParisIndex.get(0))), ranks);
        } else {
            return of(0, ranks);
        }
    }

    private LinkedHashMap<Integer, Integer> grouping(List<Integer> ranks) {
        return ranks.stream().collect(groupingBy(Integer::intValue, counting())).
                entrySet().stream().sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue())).
                collect(toMap(o -> o.getKey(), o -> o.getValue().intValue(), (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * def hand_rank(hand):
     * "Return a value indicating the rank of a hand."
     * ranks = card_ranks(hand)
     * if straight(ranks) and flush(hand):
     * return (8, max(ranks))
     * elif kind(4, ranks):
     * return (7, kind(4, ranks), kind(1, ranks))
     * elif kind(3, ranks) and kind(2, ranks):
     * return (6, kind(3, ranks), kind(2, ranks))
     * elif flush(hand):
     * return (5, ranks)
     * elif straight(ranks):
     * return (4, max(ranks))
     * elif kind(3, ranks):
     * return (3, kind(3, ranks), ranks)
     * elif two_pair(ranks):
     * return (2, two_pair(ranks), ranks)
     * elif kind(2, ranks):
     * return (1, kind(2, ranks), ranks)
     * else:
     * return (0, ranks)
     * return
     *
     * @param ranks
     * @return
     */

    //returns True if the ordered ranks form a 5-card straight
    @VisibleForTesting
    protected boolean straight(List<Integer> ranks) {
        List<Integer> sorted = ranks.stream().sorted(Integer::compareTo).collect(toList());
        return (sorted.get(sorted.size() - 1) - sorted.get(0) == 4) &&
                (sorted.stream().distinct().count() == 5);
    }

    //returns True if all the cards have the same suits.
    @VisibleForTesting
    protected boolean flush(String[] hands) {
        return Arrays.stream(hands).map(o -> o.charAt(1)).distinct().count() == 1;
    }

    @VisibleForTesting
    //returns an ORDERED tuple of the ranks, in a hand (where the order goes from highest to lowest rank).
    protected List<Integer> cardRank(String[] hands) {
        List<Integer> sortedRank = Arrays.stream(hands).
                map(o -> cardToInt(o)).sorted((o1, o2) -> Integer.compare(o2, o1)).collect(toList());
        if (sortedRank.equals(LOWER_STRAIGHT_REP)) {
            return LOWER_STRAIGHT;
        } else {
            return sortedRank;
        }
    }

    private final String INDICS = "--23456789TJQKA";

    private final List<Integer> LOWER_STRAIGHT_REP = of(14, 4, 3, 2, 1);

    private final List<Integer> LOWER_STRAIGHT = of(5, 4, 3, 2, 1);

    private Integer cardToInt(String str) {
        Character ch = str.toCharArray()[0];
        return INDICS.indexOf(ch);
    }

    //returns the first rank that the hand has exactly n of. For A hand with 4 sevens this function would return 7.
    //return null if there is no n-of-a-kind in the hand
    @VisibleForTesting
    protected Integer kind(int n, List<Integer> ranks) {
        Map<Integer, Long> occurrences = ranks.stream().collect(groupingBy(Integer::intValue, counting()));
        return occurrences.entrySet().stream().
                filter(o -> o.getValue().equals((long) n)).findAny().map(Map.Entry::getKey).orElse(null);
    }

    @VisibleForTesting
    protected List<Integer> twoParis(List<Integer> ranks) {
        Map<Integer, Long> occurrences = ranks.stream().collect(groupingBy(Integer::intValue, counting()));
        return occurrences.entrySet().stream().
                filter(o -> o.getValue().equals(2l)).
                map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
