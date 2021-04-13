package com.shawn.touchstone.alg;


import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LRUCacheTest {

  @Test
  public void givenLRUShouldGetElements() {
    LRUCache cache = new LRUCache(4);
    cache.insert("a", 1);
    cache.insert("b", 2);
    cache.insert("c", 3);
    cache.insert("d", 4);
    assertThat(cache.get("a"), is(1));
    cache.insert("e", 5);
    assertThat(cache.get("b"), is(nullValue()));
    assertThat(cache.get("a"), is(1));
    assertThat(cache.get("e"), is(5));
    cache.insert("f", 6);
    assertThat(cache.get("c"), is(nullValue()));
    assertThat(cache.get("f"), is(6));
  }
}