/*
 * Copyright (C) 2019 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.collections;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ListTest {

    @Test
    public void partition() {
        List<Integer> arr = IntStream.range(0, 10).boxed().collect(toList());
        List<List<Integer>> partitions = Lists.partition(arr, 3);
        assertEquals(4, partitions.size());
        assertThat(partitions.get(0), contains(0, 1, 2));
        assertThat(arr, contains(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }
}
