package com.shawn.touchstone.functional;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamOpsExp {


    private static Stream<Dish> highest(List<Dish> dishes, int count) {
        return dishes.stream().sorted(Comparator.comparing(Dish::getCalories).reversed()).limit(count);
    }

    public static void main(String[] args) {
        List<Dish> dishes = Lists.newArrayList(
            new Dish("d1", false, 100, Dish.Type.FISH),
            new Dish("d2", false, 77, Dish.Type.OTHER),
            new Dish("d3", false, 300, Dish.Type.MEAT),
            new Dish("d4", false, 20, Dish.Type.OTHER),
            new Dish("d5", false, 120, Dish.Type.FISH));

//        highest(dishes, 3).map(Dish::getName).forEach(System.out::println);
//        slicingStreams(dishes);
        flattenStreams();
    }

    private static void slicingStreams(List<Dish> dishes) {
        dishes.stream().takeWhile(dish -> dish.getCalories() < 300).map(Dish::getName).forEach(System.out::println);
        dishes.stream().dropWhile(dish -> dish.getCalories() < 300).map(Dish::getName).forEach(System.out::println);
    }

    private static void flattenStreams() {
        String[] words = {"Hello", "World", "And", "The", "Result"};
        List<String> chs = Arrays.stream(words)
                .map(w -> w.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
        System.out.println(chs.toString());

        List<Integer> nums1 = Lists.newArrayList(1, 2, 3);
        List<Integer> nums2 = Lists.newArrayList(3, 4);
        nums1.stream()
                .flatMap(i -> nums2.stream()
                        .filter(j -> (j + i) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .forEach(p -> System.out.println(p[0] + " " + p[1]));
    }

    public static class Dish {
        private final String name;
        private final boolean vegetarian;
        private final int calories;
        private final Type type;
        public Dish(String name, boolean vegetarian, int calories, Type type) {
            this.name = name;
            this.vegetarian = vegetarian;
            this.calories = calories;
            this.type = type;
        }
        public String getName() {
            return name;
        }
        public boolean isVegetarian() {
            return vegetarian;
        }
        public int getCalories() {
            return calories;
        }
        public Type getType() {
            return type;
        }
        @Override
        public String toString() {
            return name;
        }
        public enum Type { MEAT, FISH, OTHER }
    }

}
