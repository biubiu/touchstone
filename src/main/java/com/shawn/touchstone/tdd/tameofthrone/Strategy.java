package com.shawn.touchstone.tdd.tameofthrone;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Strategy {

    public boolean matching(String msg, Kingdom kingdom) {
        if (msg == null || msg.isEmpty() || kingdom == null) {
            return false;
        }
        Map<Character, Long> occurrences = groupbyOccurrences(msg);
        return include(occurrences, kingdom.emblemCharOccurrences());
    }

    private boolean include(Map<Character, Long> msg, Map<Character, Long> emblem) {
        for (Map.Entry<Character, Long> em : emblem.entrySet()) {
            if (!msg.containsKey(em.getKey()) || (msg.get(em.getKey()) < em.getValue())) {
                return false;
            }
        }
        return true;
    }

    private Map<Character, Long> groupbyOccurrences(String str) {
        return str.trim().toLowerCase().chars().mapToObj(o -> (char)o)
                .collect(Collectors.groupingBy(c -> (Character) c, LinkedHashMap::new, Collectors.counting()));
    }

}
