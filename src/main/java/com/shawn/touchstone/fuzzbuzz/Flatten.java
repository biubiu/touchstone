package com.shawn.touchstone.fuzzbuzz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by shangfei on 23/7/17.
 */
public class Flatten {
    public static void main(String[] args) {
        Object[] arrays = new Object[]{1, 2, new Object[]{new Object[]{3, 4}, new Object[]{new Object[]{5, 6}}, 7}};

        //Object[] flatArray = Arrays.stream(arrays).flatMap(Flatten::flatten).toArray();
        //   System.out.println(Arrays.toString(flatArray));
        System.out.println(flattenByRecursion(arrays));
    }

    public static Integer[] flattenByStream(Object[] arrs){
        Object[] objArr = Arrays.stream(arrs).flatMap(Flatten::flattenToStream).toArray();
        return Arrays.copyOf(objArr, objArr.length, Integer[].class);
    }

    public static Stream flattenToStream(Object o){
        if (o instanceof Object[]) {
            return Arrays.stream((Object[]) o).flatMap(Flatten::flattenToStream);
        }
        return Stream.of(o);
    }

    public static List<Integer> flattenByRecursion(Object[] nestedNumbers) {

        if (nestedNumbers == null) return null;

        List<Integer> flattenedNumbers = new ArrayList<>();

        for (Object element : nestedNumbers) {
            if (element instanceof Integer) {
                flattenedNumbers.add((Integer)element);
            } else {
                flattenedNumbers.addAll(flattenByRecursion((Object[]) element));
            }
        }
        return flattenedNumbers;
    }
}
