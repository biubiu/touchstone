package com.shawn.touchstone.cs212.pokergame;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Poker {

    private Comparator<List<Integer>> rankListComparator = (o1, o2) -> {
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

    Comparator<String[]> handComparator = (o1, o2) -> {
        List h1 = handRank(o1);
        List h2 = handRank(o2);
        Integer h1Mark = (Integer) h1.get(0);
        Integer h2Mark = (Integer) h2.get(0);
        int compare = h1Mark.compareTo(h2Mark);
        if (compare != 0) return compare;
        else {
            if (h1Mark == 8) {
                return Integer.compare((int) h1.get(1), (int) h2.get(1));
            } else if (h1Mark == 7) {
                int compare1 = Integer.compare((int) h1.get(1), (int) h2.get(1));
                if (compare1 != 0) return compare1;
                else {
                    int compare2 = Integer.compare((int) h1.get(2), (int) h2.get(2));
                    return compare2;
                }
            } else if (h1Mark == 6) {
                int compare1 = Integer.compare((int) h1.get(1), (int) h2.get(1));
                if (compare1 != 0) return compare1;
                else {
                    int compare2 = Integer.compare((int) h1.get(2), (int) h2.get(2));
                    return compare2;
                }
            } else if (h1Mark == 5) {
                return rankListComparator.compare((List<Integer>) h1.get(1), (List<Integer>) h2.get(1));
            } else if (h1Mark == 4) {
                return Integer.compare((int) h1.get(1), (int) h2.get(1));
            } else if (h1Mark == 3) {
                int compare1 = Integer.compare((int) h1.get(1), (int) h2.get(1));
                if (compare1 != 0) return compare1;
                else return rankListComparator.compare((List<Integer>) h1.get(2), (List<Integer>) h2.get(2));
            } else if (h1Mark == 2) {
                int compare1 = rankListComparator.compare((List<Integer>) h1.get(1), (List<Integer>) h2.get(1));
                if (compare1 != 0) return compare1;
                else return rankListComparator.compare((List<Integer>) h1.get(2), (List<Integer>) h2.get(2));
            } else if (h1Mark == 1) {
                int compare1 = Integer.compare((int) h1.get(1), (int) h2.get(1));
                if (compare1 != 0) return compare1;
                else return rankListComparator.compare((List<Integer>) h1.get(2), (List<Integer>) h2.get(2));
            } else {
                return rankListComparator.compare((List<Integer>) h1.get(1), (List<Integer>) h2.get(1));
            }
        }
    };

    public String[] findHand(List<String[]> hands) {
        if (hands == null || hands.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<String[]> sorted = Ordering.from(handComparator).reverse().sortedCopy(hands);
        return sorted.get(0);
    }


    public List handRank(String[] hands) {
        List<Integer> ranks = cardRank(hands);
        Integer max = ranks.get(0);
        Boolean isStraight = straight(ranks);
        Boolean isFlush = flush(hands);
        LinkedHashMap<Integer, Integer> occurrences = groupingSortedDesc(ranks);
        List<Integer> descCounts = new ArrayList<>(occurrences.values());
        List<Integer> ranksByCounts = new ArrayList<>(occurrences.keySet());
        List<Integer> twoParisIndex = IntStream.range(0, descCounts.size()).boxed()
                .filter(in -> descCounts.get(in).equals(2)).collect(toList());
        if (isStraight && isFlush) {
            return newArrayList(8, max);
        } else if (descCounts.contains(4)) {
            return newArrayList(7, ranksByCounts.get(descCounts.indexOf(4)), ranksByCounts.get(descCounts.indexOf(1)));
        } else if (descCounts.contains(3) && descCounts.contains(2)) {
            return newArrayList(6, ranksByCounts.get(descCounts.indexOf(3)), ranksByCounts.get(descCounts.indexOf(2)));
        } else if (isFlush) {
            return newArrayList(5, ranks);
        } else if (isStraight) {
            return newArrayList(4, max);
        } else if (descCounts.contains(3)) {
            return newArrayList(3, descCounts.get(descCounts.indexOf(3)), ranks);
        } else if (twoParisIndex.size() == 2) {
            return newArrayList(2, newArrayList(ranksByCounts.get(twoParisIndex.get(0)), ranksByCounts.get(twoParisIndex.get(1))), ranks);
        } else if (twoParisIndex.size() == 1) {
            return newArrayList(1, ranksByCounts.get(twoParisIndex.get(0)), ranks);
        } else {
            return newArrayList(0, ranks);
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
