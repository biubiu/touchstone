package com.shawn.touchstone.alg;

import java.util.Collections;
import java.util.PriorityQueue;

public class SlidingWindowMedian {

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            if (maxHeap.isEmpty() || maxHeap.peek() >= nums[i]){
                maxHeap.offer(nums[i]);
            }else {
                minHeap.offer(nums[i]);
            }
            rebalanced();
            if (i - k + 1 >= 0) {
                if (maxHeap.size() == minHeap.size()) {
                    result[i - k + 1] = maxHeap.peek() / 2.0 + minHeap.peek() / 2.0;
                } else {
                    result[i - k + 1] = maxHeap.peek() ;
                }
                int numToRemoved = nums[i - k + 1];
                if (numToRemoved <= maxHeap.peek()) {
                    maxHeap.remove(numToRemoved);
                } else {
                    minHeap.remove(numToRemoved);
                }
                rebalanced();
            }
        }
        return result;
    }

    private void rebalanced() {
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

}
