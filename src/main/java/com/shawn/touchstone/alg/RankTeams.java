package com.shawn.touchstone.alg;

import java.util.HashMap;
import java.util.Map;

public class RankTeams {

    public String rankTeams(String[] votes) {
//        Map<Character, Long> occurrences = Arrays.stream(votes).map(String::chars).
//                flatMapToInt(i -> i).mapToObj(i -> (char)i).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        String result = occurrences.keySet().stream()
//                .sorted(Comparator.comparingLong(occurrences::get).reversed()).reduce("", (a, b) -> a + b, String::concat);
        int size = votes[0].length();
        Map<Character, int[]> voteCounts = new HashMap<>();
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                char ch = vote.charAt(i);
                voteCounts.computeIfAbsent(ch, k -> new int[size])[i]++;
            }
        }

        return voteCounts.entrySet().stream().sorted((a, b) -> {
            for (int i = 0; i < size; i++) {
                if (a.getValue()[i] != b.getValue()[i]) {
                    return b.getValue()[i] - a.getValue()[i];
                }
            }
            return 0;
        }).map(Map.Entry::getKey).reduce("", (a, b) -> a + b, String::concat);
    }
}
