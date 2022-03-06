package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class FileSys {

    private int totalSize;
    private PriorityQueue<Collection> maxPQ = new PriorityQueue<>((a, b) -> Integer.compare(b.size, a.size));
    private Map<String, Collection> existing = new HashMap<>();

    public void addFile(String name, int size, String collectionName) {
        File file = new File(name, size);
        this.totalSize += size;
        if (collectionName == null) return;
        Collection collection;
        if (existing.containsKey(collectionName)) {
            collection = existing.get(collectionName);
            maxPQ.remove(collection);
            collection.setSize(collection.getSize() + size);
        } else {
            collection = new Collection(collectionName, size);
            existing.put(collectionName, collection);
        }
        maxPQ.offer(collection);

    }

    public int getTotalSize() {
        return totalSize;
    }

    public List<Collection> topK(int k) {
        int size = maxPQ.size() >= k ? k : maxPQ.size();
        List<Collection> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(maxPQ.poll());
        }
        return list;
    }

    record File(String name, int size) {

    }

    static class Collection {
        private int size;
        private String name;

        public Collection(String name, int size) {
            this.size = size;
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Collection that = (Collection) o;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
