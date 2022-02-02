package com.shawn.touchstone.functional;

import java.util.List;
import java.util.Optional;

public class Reduction {

    public static void main(String[] args) {
        List<Integer> nums = List.of(1, 2, 3, 4, 5);
        Optional<Integer> res = nums.stream().reduce((a, b) -> a + b);
        int sum = nums.stream().reduce(0, Integer::sum);
        System.out.println(sum + " " + res);

    }
}
