/*
 * Copyright (C) 2019 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.collections;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListTest {

    @Test
    public void partition() {
        List<Integer> arr = IntStream.range(0, 10).boxed().collect(toList());
        List<List<Integer>> partitions = Lists.partition(arr, 3);

        assertEquals(4, partitions.size());
        assertThat(partitions.get(0), contains(0, 1, 2));
        assertThat(arr, contains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test
    public void testArraysToList(){
        List<String> a = Arrays.asList("a", "b", "c");
        a.set(0, "m");

        assertThat(a.get(0), is("m"));
        assertThrows(UnsupportedOperationException.class, () -> a.add("d"));
    }

    @Test
    public void testConvariantList() {
        List<String> generics = null;
        List nonGenerics = new ArrayList(10);
        nonGenerics.add(new Object());
        nonGenerics.add(new Integer(2));

        generics = nonGenerics;
        System.out.println(";");
        List<String> finalGenerics = generics;
        assertThrows(ClassCastException.class, () -> {
            String str = finalGenerics.get(0);
        });
    }

    @Test
    public void testRemovingEle() {
        List<String> strs = Lists.newArrayList("a", "b",  "c");
        strs.remove("a");
        assertThat(strs.size(), is(2));

        Iterator<String> its = strs.iterator();
        while(its.hasNext()) {
            String e = its.next();
            if (e.equals("c")) {
                its.remove();
            }
        }
        assertThat(strs.size(), is(1));

        strs.add("c");
        assertThrows(ConcurrentModificationException.class, () -> {
            for (String st: strs) {
                if ("c".equals(st)) {
                    strs.remove(st);
                }
            }
        });
    }

    @Test
    public void testSwitchWithNull() {
        assertThrows(NullPointerException.class, () -> switchMethod(null));
    }

    private void switchMethod(String param) {
        switch (param) {
            case "sth":
                System.out.printf("sth /n");
                break;
            case "null":
                System.out.printf("null str /n");
                break;
            default:
                System.out.printf("default");
        }
    }
}
