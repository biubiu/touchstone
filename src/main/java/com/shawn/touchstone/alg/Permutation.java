package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.List;

public class Permutation {

  public List<List<Integer>> generate(List<Integer> array) {
    List<List<Integer>> result = new ArrayList<>();
    //generate(array, new ArrayList<>(), result);
    generate(0, array, result);
    return result;
  }

  private void generate(int idx, List<Integer> arr, List<List<Integer>> result) {
      if (idx == arr.size() - 1) {
        result.add(new ArrayList<>(arr));
      } else {
        for (int j = idx; j < arr.size(); j++) {
          swap(arr, idx, j);
          generate(idx + 1, arr, result);
          swap(arr, idx, j);
        }
      }
  }

  private void swap(List<Integer> arr, int i, int j) {
    int tmp = arr.get(i);
    arr.set(i, arr.get(j));
    arr.set(j, tmp);
  }

  private void generate(List<Integer> array, List<Integer> curr, List<List<Integer>> result) {
    if (array.size() == 0 && curr.size() > 0) {
      result.add(curr);
    } else {
      for (int i = 0; i < array.size(); i++) {
        List<Integer> newArr = new ArrayList<>(array);
        newArr.remove(i);
        List<Integer> newCurr = new ArrayList<>(curr);
        newCurr.add(array.get(i));
        generate(newArr, newCurr, result);
      }
    }
  }
}
