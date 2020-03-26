package com.shawn.touchstone.collections;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MapTest {

    @Test
    public void testLinkedHashMapAsLRU() {
        Map<Integer, Integer> lru = new LinkedHashMap(10, 0.75f, true);
        lru.putIfAbsent(1, 1);
        lru.putIfAbsent(2, 2);
        lru.putIfAbsent(3, 3);
        lru.putIfAbsent(4, 4);

        lru.entrySet().forEach(System.out::println);
        System.out.println();
        lru.put(3, 44);
        lru.entrySet().forEach(System.out::println);
    }

    @Test
    public void testNavigatableMap() {
        NavigableMap<Integer, Long> map = new ConcurrentSkipListMap(Comparator.naturalOrder());
        map.put(1, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

    }
}
