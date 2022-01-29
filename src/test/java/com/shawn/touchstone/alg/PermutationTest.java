package com.shawn.touchstone.alg;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class PermutationTest {

  @Test
  public void generate() {
   List<List<Integer>> result = new Permutation().generate(newArrayList(1, 2, 3));
   assertThat(result, hasSize(6));
   assertThat(result, containsInAnyOrder(of(1, 2, 3), of(1, 3, 2), of(2, 1, 3),
       of(2, 3,1 ), of(3, 1, 2), of(3, 2, 1)));
  }
}