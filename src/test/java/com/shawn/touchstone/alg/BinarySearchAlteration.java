package com.shawn.touchstone.alg;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BinarySearchAlteration {

    public int ceiling(int[] arr, int key) {
        if (arr[0] > key) return -1;
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (key < arr[mid]) {
                right = mid - 1;
            } else if (key > arr[mid]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }

    public int floor(int[] arr, int key) {
        if (arr[0] > key) return -1;
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
//            if (key < arr[mid]) {
//                right = mid - 1;
//            } else if (key > arr[mid]) {
//                left = mid + 1;
//            } else {
//                return mid;
//            }
            if (arr[mid] > key) {
                right = mid - 1;
            } else {
                if ((mid == arr.length - 1 || arr[mid + 1] > key)) return mid;
                left = mid + 1;
            }
        }
        return right;
    }

    public int[] findRange(int[] arr, int key) {
        int[] result = new int[] { -1, -1 };
        int idx = search(arr, key, false);
        if (idx != -1) {
            result[0] = idx;
            result[1] = search(arr, key, true);
        }
        return result;
    }

    private int search(int[] arr, int key, boolean findMax) {
        int keyIdx = -1;
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (key < arr[mid]) {
                right = mid - 1;
            } else if (key > arr[mid]) {
                left = mid + 1;
            } else {
                keyIdx = mid;
                if (findMax) left = mid + 1;
                else right = mid - 1;
            }
        }
        return keyIdx;
    }

    public char nextLetter(char[] chars, char key) {
        int left = 0, right = chars.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (chars[mid] > key) {
                right--;
            } else {
                left++;
            }
        }
        return chars[left % chars.length];
    }

    class ArrayReader {
        int[] arr;

        ArrayReader(int[] arr) {
            this.arr = arr;
        }

        public int get(int index) {
            if (index >= arr.length)
                return Integer.MAX_VALUE;
            return arr[index];
        }
    }

    public int searchInfiniteArr(ArrayReader reader, int key) {
        int left = 0, right = 1;
        while (reader.get(right) < key) {
            int newLeft = right + 1;
            right += (right - left + 1) * 2;
            left = newLeft;
        }
        return search(reader, key, left, right);
    }

    private int search(ArrayReader reader, int key, int left, int right) {
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (key < reader.get(mid)) {
                right = mid - 1;
            } else if (key > reader.get(mid)) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return - 1;
    }

    @Test
    public void searchInfiniteArr() {
        ArrayReader reader = new ArrayReader(new int[] { 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30 });
        assertEquals(6, searchInfiniteArr(reader, 16));
        assertEquals(-1, searchInfiniteArr(reader, 11));
    }

    @Test
    public void searchRange() {
        int[] values = {1, 3, 8, 10, 15};

        assertArrayEquals(new int[]{3, 3}, findRange(values, 10));
        assertArrayEquals(new int[]{-1, -1}, findRange(values, 12));
    }
    @Test
    public void ceilingNumber() {
        int[] values = new int[]{1, 3, 8, 10, 15};
        int key = 12;

        assertThat(ceiling(values, key), is(4));
    }

    @Test
    public void floorNumber() {
        int[] values = new int[]{1, 3, 8, 10, 15};
        int key = 12;

        assertThat(floor(values, key), is(3));
    }


    @Test
    public void nextLetter() {
        char[] chars = {'a', 'c', 'f', 'h'};

        assertEquals( 'f', nextLetter(chars, 'c'));
        assertEquals( 'a', nextLetter(chars, 'm'));
        assertEquals( 'a', nextLetter(chars, 'h'));
    }


}
