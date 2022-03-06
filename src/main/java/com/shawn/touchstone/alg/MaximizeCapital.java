package com.shawn.touchstone.alg;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Input: Project Capitals=[0,1,2,3], Project Profits=[1,2,3,5], Initial Capital=0, Number of Projects=3
 * Output: 8
 */
public class MaximizeCapital {

    public static int findMaximumCapital(int[] capital, int[] profits, int numberOfProjects, int initialCapital) {
        int n = profits.length;
        PriorityQueue<Integer> minCapitalHeap = new PriorityQueue<>(n, Comparator.comparingInt(a -> capital[a]));
        PriorityQueue<Integer> maxProfitsHeap = new PriorityQueue<>(n,
                (a, b) -> Integer.compare(profits[b], profits[a]));
        for (int i = 0; i < n; i++) {
            minCapitalHeap.offer(i);
        }
        int availableCapital = initialCapital;
        for (int j = 0; j < numberOfProjects; j++) {
            while (!minCapitalHeap.isEmpty() && capital[minCapitalHeap.peek()] <= availableCapital) {
                maxProfitsHeap.offer(minCapitalHeap.poll());
            }
            if (!maxProfitsHeap.isEmpty()) break;
            availableCapital += profits[maxProfitsHeap.poll()];
        }
        return availableCapital;
    }
}
