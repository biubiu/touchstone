package com.shawn.touchstone.alg;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ValueOrdered {

    @Test
    public void orderByValue() {

        assertThat(regionalMostFreq(new int[]{1, 2, 3, 3}, 2), is(new int[]{1, 1, 2}));
        assertThat(regionalMostFreq(new int[]{1, 2, 2, 2, 3, 3, 3, 2}, 3), is(new int[]{2, 3, 2, 2, 3, 2}));
    }

    public int[] regionalMostFreq(int[] arr, int m) {
        Map<Integer, Integer> freqs = new HashMap<>();
        int start = 0;

        int[] res = new int[arr.length - m + 1];
        for (int end = 0; end < arr.length; end++) {
            int curr = arr[end];
            freqs.put(curr, freqs.getOrDefault(curr, 0) + 1);
            if (end >= m - 1) {
                int val = freqs.values().stream().max(Integer::compareTo).get();
                res[start] = val;
                int head = arr[start];
                int newFreq = freqs.get(head) - 1;
                if (newFreq < 1) {
                    freqs.remove(head);
                } else {
                    freqs.put(head, newFreq);
                }
                start++;
            }
        }
        return res;
    }
    
}
