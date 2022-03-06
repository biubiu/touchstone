package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Permutation {

  public List<List<Integer>> generate(List<Integer> array) {
    List<List<Integer>> result = new ArrayList<>();
    //generate(array, new ArrayList<>(), result);
    //generate(0, array, result);
    generate(array, 0, new ArrayList<>(), result);
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

  private void generate(List<Integer> array, int idx, List<Integer> curr, List<List<Integer>> result) {
    if (idx == array.size()) {
      result.add(curr);
    } else {
      for (int i = 0; i <= curr.size(); i++) {
        List<Integer> newPerm = new ArrayList<>(curr);
        newPerm.add(i, array.get(idx));
        generate(array, idx + 1, newPerm, result);
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

  public static List<String> findLetterCaseStringPermutations(String str) {
    List<String> permutations = new ArrayList<>();
    permutations.add(str);
    for (int i = 0; i < str.length(); i++) {
      if (Character.isLetter(str.charAt(i))) {
        int n = permutations.size();
        for (int j = 0; j < n; j++) {
          char[] chars = permutations.get(j).toCharArray();
          if (Character.isUpperCase(chars[i])) {
            chars[i] = Character.toLowerCase(chars[i]);
          } else {
            chars[i] = Character.toUpperCase(chars[i]);
          }
          permutations.add(String.valueOf(chars));
        }
      }
    }
    return permutations;
  }

}
