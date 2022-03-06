package com.shawn.touchstone.alg;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

  private int maxSize;
  private int currSize;
  private DoublyLinkedList mostRecentVisited;
  private Map<String, Node> cache;

  public LRUCache(int size) {
    this.maxSize = size > 1 ? size : 1;
    this.currSize = 0;
    this.mostRecentVisited = new DoublyLinkedList();
    this.cache = new HashMap<>();
  }

  public void insert(String key, int val) {
    if (!cache.containsKey(key)) {
      if (currSize == maxSize) {
        removeLeastRecent();
      } else {
        currSize++;
      }
      cache.put(key, new Node(key, val));
    } else {
      replaceKey(key,val);
    }
    updateMostRecent(cache.get(key));
  }

  private void replaceKey(String key, int val) {
    cache.get(key).val = val;
  }

  private void removeLeastRecent() {
    String toRemove = mostRecentVisited.tail.key;
    mostRecentVisited.removeTail();
    cache.remove(toRemove);
  }

  private void updateMostRecent(Node node) {
    mostRecentVisited.setHeadTo(node);
  }

  public Integer get(String key) {
    if (cache.containsKey(key)) {
      Node node = cache.get(key);
      updateMostRecent(node);
      return node.val;
    } else {
      return null;
    }
  }

  public String getMostRecentKey() {
    if (mostRecentVisited.head != null) {
      return mostRecentVisited.head.key;
    } else {
      return null;
    }
  }

  static class DoublyLinkedList {
    Node head;
    Node tail;

    public void removeTail() {
      if (tail == null) {
        return;
      }
      if (tail == head) {
        head = null;
        tail = null;
        return;
      }
      tail = tail.prev;
      tail.next = null;
    }

    public void setHeadTo(Node node) {
      if (head == node) return;
      if (head == null) {
        head = node;
        tail = node;
      } else if (head == tail) {
        tail.prev = node;
        head = node;
        node.next = tail;
      } else {
        if (tail == node) {
          removeTail();
        }
        node.unbinding();
        head.prev = node;
        node.next = head;
        head = node;
      }
    }
  }

  static class Node {
    String key;
    int val;
    Node prev;
    Node next;

    public Node(String key, int val) {
      this.key = key;
      this.val = val;
    }

    public void unbinding() {
      if (this.prev != null) {
        prev.next = next;
      }
      if (this.next != null) {
        next.prev = prev;
      }
      this.prev = null;
      this.next = null;
    }
  }
}
