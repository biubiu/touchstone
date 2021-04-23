package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.List;

public class Subsets {

    static List<List<Integer>> subsets(List<Integer> list) {
        if (list.isEmpty()) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(list);
            return ans;
        }
        Integer first = list.get(0);
        List<Integer> rest = list.subList(1, list.size());
        List<List<Integer>> subAns1 = subsets(rest);
        List<List<Integer>> subAns2 = insertAll(first, subAns1);
        return concat(subAns1, subAns2);
    }

    private static List<List<Integer>> concat(List<List<Integer>> subAns1, List<List<Integer>> subAns2) {
        List<List<Integer>> ans = new ArrayList<>(subAns1);
        ans.addAll(subAns2);
        return ans;
    }

    private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> subAns1) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> list: subAns1) {
            List<Integer> copied = new ArrayList<>();
            copied.add(first);
            copied.addAll(list);
            result.add(copied);
        }
        return result;
    }
}
