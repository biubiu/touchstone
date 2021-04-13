package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinMaxStack {

    private List<Integer> arr;
    private List<Map<VALUE, Integer>> minMaxStack;

    public MinMaxStack() {
        arr = new ArrayList<>();
        minMaxStack = new ArrayList<>();
    }

    public int peek() {
        if (size() == 0) {
            throw new RuntimeException("empty stack");
        }
        return arr.get(size() - 1);
    }

    public int pop() {
        if (size() == 0) {
            throw new RuntimeException("empty stack");
        }
        ArrayList<Integer> arr = new ArrayList<>();
        minMaxStack.remove(minMaxStack.size() - 1);
        return arr.remove(arr.size() - 1);
    }

    public void push(Integer number) {
        arr.add(number);
        updateMinMax(number);
    }

    private void updateMinMax(Integer num) {
       Map<VALUE, Integer> newPair = new HashMap<>();
       newPair.put(VALUE.MAX, num);
        newPair.put(VALUE.MIN, num);
        if (minMaxStack.size() > 0) {
            Map<VALUE, Integer> lastMinMax =  minMaxStack.get(minMaxStack.size() - 1);
            newPair.replace(VALUE.MAX, Math.max(lastMinMax.get(VALUE.MAX), num));
            newPair.replace(VALUE.MIN, Math.min(lastMinMax.get(VALUE.MIN), num));
        }
        minMaxStack.add(newPair);
    }

    public int getMin() {
        return minMaxStack.get(minMaxStack.size() - 1).get(VALUE.MIN);
    }

    public int getMax() {
        return minMaxStack.get(minMaxStack.size() - 1).get(VALUE.MAX);
    }

    private int size() {
        return arr.size();
    }

    private enum VALUE {
        MAX,
        MIN
    }

}
