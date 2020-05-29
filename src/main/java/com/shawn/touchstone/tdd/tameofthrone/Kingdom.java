package com.shawn.touchstone.tdd.tameofthrone;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum Kingdom {
    LAND("panda"),
    WATER("octopus"),
    ICE("mammoth"),
    AIR("owl"),
    FIRE("dragon");

    private String emblem;

    private Map<Character, Long> emblemCharOccurrences;
    private Kingdom(String emblem) {
        this.emblem = emblem;

        emblemCharOccurrences = emblem.trim().toLowerCase().chars().mapToObj(o -> (char)o)
                .collect(Collectors.groupingBy(c -> (Character) c, LinkedHashMap::new, Collectors.counting()));
    }

    public Map<Character, Long> emblemCharOccurrences() {
        return emblemCharOccurrences;
    }
}
