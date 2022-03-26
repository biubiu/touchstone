package com.shawn.touchstone.alg;

import java.util.PriorityQueue;

public class NextInterval {

    int[] nextInterval(Interval[] intervals) {
        PriorityQueue<Integer> maxStart = new PriorityQueue<>((a, b) ->
                Integer.compare(intervals[b].start(), intervals[a].start()));
        PriorityQueue<Integer> maxEnd = new PriorityQueue<>((a, b) ->
                Integer.compare(intervals[b].end(), intervals[a].end()));
        int len = intervals.length;
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            maxStart.add(i);
            maxEnd.add(i);
        }
        while (!maxEnd.isEmpty()) {
            int topEnd = maxEnd.poll();
            result[topEnd] = -1;
            if (intervals[maxStart.peek()].start() >= intervals[topEnd].end()) {
                int topStart = maxStart.poll();
                while (!maxStart.isEmpty() && intervals[maxStart.peek()].start() >= intervals[topEnd].end()) {
                    topStart = maxStart.poll();
                }
                result[topEnd] = topStart;
                maxStart.add(topStart);
            }
        }
        return result;
    }
}
