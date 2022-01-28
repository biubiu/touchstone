package com.shawn.touchstone.alg;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {

    List<Integer> heap;

    public MinHeap(List<Integer> array) {
        heap = buildHeap(new ArrayList<>(array));
    }

    public List<Integer> buildHeap(List<Integer> array) {
        int firstParentIdx = (array.size() - 2) / 2;
        for (int i = firstParentIdx; i >= 0; i--) {
            siftDown(i, array.size() - 1, array);
        }
        return array;
    }

    public void siftDown(int currentIdx, int endIdx, List<Integer> heap) {
        int childOneIdx = currentIdx * 2 + 1;
        while (childOneIdx <= endIdx) {
            int childTwoIdx = currentIdx * 2 + 2;
            childTwoIdx = childTwoIdx <= endIdx ? childTwoIdx : -1;
            int idxToSwap = childOneIdx;
            if (childTwoIdx != -1 && heap.get(childTwoIdx) < heap.get(childOneIdx)) {
                idxToSwap = childTwoIdx;
            }
            if (heap.get(idxToSwap) < heap.get(currentIdx)) {
                swap(idxToSwap, currentIdx, heap);
                currentIdx = idxToSwap;
                childOneIdx = currentIdx * 2 + 1;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j, List<Integer> heap) {
        int tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    public void siftUp(int currentIdx, List<Integer> heap) {
        int parentIdx = (currentIdx - 1) / 2;
        while (currentIdx > 0 && heap.get(currentIdx) < heap.get(parentIdx)) {
            swap(currentIdx, parentIdx, heap);
            currentIdx = parentIdx;
            parentIdx = (currentIdx - 1) / 2;
        }
    }

    public int peek() {
        return heap.get(0);
    }

    public int remove() {
        swap(0, heap.size() - 1, heap);
        int valToRemove = heap.get(heap.size() - 1);
        heap.remove(heap.size() - 1);
        siftDown(0, heap.size() - 1, heap);
        return valToRemove;
    }

    public void insert(int value) {
        heap.add(value);
        siftUp(heap.size() - 1, heap);
    }

}
