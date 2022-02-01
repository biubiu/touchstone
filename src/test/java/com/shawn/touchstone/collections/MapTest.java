package com.shawn.touchstone.collections;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

    @Test
    public void testMerge() {
        Map<Character, Integer> map = new HashMap<>();
        map.merge('a', 1, (c, n) -> c + 1);
        map.merge('a', 1, (c, n) -> c + 1);
        map.merge('a', 1, (c, n) -> c + 1);
        assertThat(map.get('a'), is(3));
    }

    @Test
    public void testGrouping() {
        List<String> words = List.of("abc", "cba", "abc", "acd");
        Map<String, Long> freq = words.stream().collect(groupingBy(Function.identity(), counting()));
        assertThat(freq.size(), is(3));
        assertThat(freq.get("abc"), is(2L));
        assertThat(freq.get("cba"), is(1L));
        assertThat(freq.get("acd"), is(1L));

    }
}
