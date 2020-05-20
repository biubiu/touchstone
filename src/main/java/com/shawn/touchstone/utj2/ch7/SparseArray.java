package com.shawn.touchstone.utj2.ch7;

import java.util.Arrays;

public class SparseArray<T> {
    public static final int INITIAL_SIZE = 1000;

    private int[] keys = new int[INITIAL_SIZE];

    private Object[] values = new Object[INITIAL_SIZE];

    private int size = 0;

    public void put(int key, T val) {
        if (val == null) return;
        int index = binarySearch(key, keys, size);
        if (index != -1 && keys[index] == key) {
            values[index] = val;
        } else {
            insertAfter(key, val, index);
        }
    }

    public int size() {
        return size;
    }

    public void insertAfter(int key, T newVal, int index) {
        int[] newKeys = new int[INITIAL_SIZE];
        Object[] newValues = new Object[INITIAL_SIZE];
        copyFromBefore(index, newKeys, newValues);

        int newIndex = index + 1;
        newKeys[index] = key;
        newValues[index] = newVal;

        if (size - newIndex != 0) {
            copyFromAfter(index, newKeys, newValues);
        }
        keys = newKeys;
        values = newValues;
        size++;
    }

    private void copyFromAfter(int index, int[] newKeys, Object[] newValues) {
        int start = index + 1;
        System.arraycopy(keys, start, newKeys, start + 1, size - start);
        System.arraycopy(values, start, newValues, start + 1, size - start);
    }

    private void copyFromBefore(int index, int[] newKeys, Object[] newValues) {
        System.arraycopy(keys, 0, newKeys, 0, index + 1);
        System.arraycopy(values, 0, newValues, 0, index + 1);
    }

    public T get(int key) {
        int index = Arrays.binarySearch(keys, key);
        if (index != -1 && keys[index] == key) {
            return (T) values[index];
        } else {
            return null;
        }
    }

    int binarySearch(int index, int[] arr, int size) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (index > arr[mid]) {
                low = mid + 1;
            } else if ( index < arr[mid]) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return low - 1;
    }
}
