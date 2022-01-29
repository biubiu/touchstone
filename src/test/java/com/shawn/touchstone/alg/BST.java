package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BST {

  public int value;
  public BST left;
  public BST right;

  public BST(int value) {
    this.value = value;
  }

  public BST insert(int value) {
    if (this.value > value) {
      if (left == null) {
        left = new BST(value);
      } else {
        left.insert(value);
      }
    } else {
      if (right == null) {
        right = new BST(value);
      } else {
        right.insert(value);
      }
    }
    return this;
  }

  public boolean contains(int value) {
    if (this.value > value) {
      if (left == null) {
        return false;
      } else {
        return left.contains(value);
      }
    } else if (this.value < value) {
      if (right == null) {
        return false;
      } else {
        return right.contains(value);
      }
    } else {
      return true;
    }
  }

  public BST remove(int value) {
    List<Integer> list = new ArrayList<>();
    // Write your code here.
    // Do not edit the return statement of this method.
    return this;
  }
}
