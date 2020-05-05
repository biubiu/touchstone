package com.shawn.touchstone.cs212.pokergame;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Chars;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

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
            return Arrays.stream(LOWER_STRAIGHT).boxed().collect(toList());
        } else {
            return sortedRank;
        }
    }

    private static final String INDICS = "--23456789TJQKA";

    private static final List<Integer> LOWER_STRAIGHT_REP = newArrayList(14, 5, 4, 3, 2);

    private static final int[] LOWER_STRAIGHT = new int[]{5, 4, 3, 2, 1};

    private Integer cardToInt(String str) {
        Character ch = str.toCharArray()[0];
        return INDICS.indexOf(ch);
    }

    public String[] bestHand(String[] hands) {
        Set<String[]> combination = this.combinnation(hands, 5, String.class);

        //List<String[]> rawhands = combination.stream().map(o -> (new String[]) o).collect(toList());
        return this.findHand(Lists.newArrayList(combination));
    }

    public <T> Set<T[]> combinnation(T[] arr, int k, Class clazz) {
        int len = arr.length;
        if (k > len) {
            throw new IllegalArgumentException("k exceeds arr len");
        }
        if (k == 0) {
            return emptySet();
        }
        if (k == len) {
            return ImmutableSet.of(arr);
        }
        Set<T[]> result = new HashSet<>();
        // combination index arr
        int[] ptrs = new int[k];

        int r = 0; // index of combination index arr
        int i = 0; // index of arr
        while (r >= 0) {
            if (i <= (len + (r - k))) {
                ptrs[r] = i;

                // if combination array is full print and increment i;
                if(r == k-1){
                    result.add(subArr(k, arr, ptrs, clazz));
                    i++;
                } else {
                    i = ptrs[r] + 1;
                    r++;
                }
            } else {
                r--;
                if (r >= 0) {
                    i = ptrs[r] + 1;
                }
            }
        }
        return result;
    }

    private <T> T[] subArr(int k, T[] arr, int[] ptrs, Class clazz) {
        //@SuppressWarnings("unchecked")
        //T[] tmp = (T[]) new Object[k];
        T[] tmp = (T[])Array.newInstance(clazz, k);
        for (int i = 0; i < ptrs.length; i++) {
            tmp[i] =  arr[ptrs[i]];
        }
        return tmp;
    }

    private static final String LIST = "23456789TJQKA";
    
    private static final Set<String> BLACK_CARDS = expandWildCards("D", "H");

    private static final  Set<String> RED_CARDS = expandWildCards("S", "C");

    private static Set<String> expandWildCards(String re1, String re2) {
        return Chars.asList(LIST.toCharArray()).stream().
                map(o -> newHashSet(o + re1, o + re2)).flatMap(Set::stream).collect(toSet());
    }

    private Set<String> replacement(String card) {
        if (card.equals("?B")) {
            return BLACK_CARDS;
        } else if (card.equals("?R")) {
            return RED_CARDS;
        }
        return newHashSet(card);
    }

    /**
     * input a 7-card hand and returns the best 5 card hand.
     *  Jokers will be treated as 'wild cards' which
     *  can take any rank or suit of the same color.
     *  Black Joker'?B', can be used as any spade or club
     *  Red joker '?R', can any heart or diamond
     */
    public String[] bestWildHand(String[] hands) {
        List<Set<String>> wildCards = Arrays.stream(hands).map(o -> replacement(o)).collect(Collectors.toList());
         Set<List<String>> product = Sets.cartesianProduct(wildCards);
         List<String[]> candidates = product.stream().map(o -> bestHand(o.toArray(new String[]{}))).collect(Collectors.toList());
        String[] best = findHand(candidates);
        return best;
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
                if (i >= o2.size()) {
                    return 1;
                }
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
