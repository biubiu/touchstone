package com.shawn.touchstone.cs212.pokergame;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Poker {

    private final List<List<Integer>> RANK_PERMUTATION = of(of(5), //0
            of(4, 1), // 1
            of(3, 2), // 2
            of(3, 1, 1), // 3
            of(2, 2, 1), // 4
            of(2, 1, 1, 1)); // 5


    public String[] findHand(List<String[]> hands) {
        if (hands == null || hands.isEmpty()) {
            throw new IllegalArgumentException();
        }
        TreeSet<HandRank> ordered = hands.stream().map(this::handRank).collect(toCollection(TreeSet::new));

        return ordered.last().hands;
    }

    public HandRank handRank(String[] hands) {
        List<Integer> ranks = cardRank(hands);
        Boolean isStraight = straight(ranks);
        Boolean isFlush = flush(hands);
        LinkedHashMap<Integer, Integer> occurrences = groupingSortedDesc(ranks);
        List<Integer> counts = new ArrayList<>(occurrences.values());
        List<Integer> ranksByCounts = new ArrayList<>(occurrences.keySet());
        if (counts.equals(RANK_PERMUTATION.get(0))) {
            return new HandRank(hands, 9, ranksByCounts);
        }
        if (isStraight && isFlush) {
            return new HandRank(hands, 8, ranksByCounts);
        } else if (counts.equals(RANK_PERMUTATION.get(1))) {
            return new HandRank(hands, 7, ranksByCounts);
        } else if (counts.equals(RANK_PERMUTATION.get(2))) {
            return new HandRank(hands, 6, ranksByCounts);
        } else if (isFlush) {
            return new HandRank(hands, 5, ranksByCounts);
        } else if (isStraight) {
            return new HandRank(hands, 4, ranksByCounts);
        } else if (counts.equals(RANK_PERMUTATION.get(3))) {
            return new HandRank(hands, 3, ranksByCounts);
        } else if (counts.equals(RANK_PERMUTATION.get(4))) {
            return new HandRank(hands, 2, ranksByCounts);
        } else if (counts.equals(RANK_PERMUTATION.get(5))) {
            return new HandRank(hands, 1, ranksByCounts);
        } else {
            return new HandRank(hands, 0, ranksByCounts);
        }
    }

    private LinkedHashMap<Integer, Integer> groupingSortedDesc(List<Integer> ranks) {
        return ranks.stream().collect(groupingBy(Integer::intValue, counting())).
                entrySet().stream().sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue())).
                collect(toMap(o -> o.getKey(), o -> o.getValue().intValue(), (e1, e2) -> e1, LinkedHashMap::new));
    }


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

    private final List<Integer> LOWER_STRAIGHT_REP = newArrayList(14, 5, 4, 3, 2);

    private final List<Integer> LOWER_STRAIGHT = newArrayList(5, 4, 3, 2, 1);

    private Integer cardToInt(String str) {
        Character ch = str.toCharArray()[0];
        return INDICS.indexOf(ch);
    }

//    //returns the first rank that the hand has exactly n of. For A hand with 4 sevens this function would return 7.
//    //return null if there is no n-of-a-kind in the hand
//    @VisibleForTesting
//    protected Integer kind(int n, List<Integer> ranks) {
//        Map<Integer, Long> occurrences = ranks.stream().collect(groupingBy(Integer::intValue, counting()));
//        return occurrences.entrySet().stream().
//                filter(o -> o.getValue().equals((long) n)).findAny().map(Map.Entry::getKey).orElse(null);
//    }
//
//    @VisibleForTesting
//    protected List<Integer> twoParis(List<Integer> ranks) {
//        Map<Integer, Long> occurrences = ranks.stream().collect(groupingBy(Integer::intValue, counting()));
//        return occurrences.entrySet().stream().
//                filter(o -> o.getValue().equals(2l)).
//                map(Map.Entry::getKey).collect(Collectors.toList());
//    }

    static class HandRank implements Comparable<HandRank> {
        String[] hands;
        List<Integer> ranks;
        int score;

        HandRank(String[] hands, int score, List<Integer> ranks) {
            this.hands = hands;
            this.ranks = ranks;
            this.score = score;
        }

        @Override
        public int compareTo(HandRank that) {
            int compare = Integer.compare(this.score, that.score);
            if (compare != 0) return compare;
            else {
                return RANK_LIST_COMPARATOR.compare(this.ranks, that.ranks);
            }
        }

        private final Comparator<List<Integer>> RANK_LIST_COMPARATOR = (o1, o2) -> {
            for (int i = 0; i < o1.size(); i++) {
                Integer v1 = o1.get(i);
                Integer v2 = o2.get(i);
                if (v2 == null) {
                    return 1;
                }
                int compare = v1.compareTo(v2);
                if (compare != 0) {
                    return compare;
                }
            }
            return 0;
        };
    }
}
