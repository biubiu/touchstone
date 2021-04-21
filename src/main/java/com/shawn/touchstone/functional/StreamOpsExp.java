package com.shawn.touchstone.functional;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamOpsExp {


    private static Stream<Dish> highest(List<Dish> dishes, int count) {
        return dishes.stream().sorted(Comparator.comparing(Dish::getCalories).reversed()).limit(count);
    }

    public static void main(String[] args) {
        List<Dish> dishes = Dish.gen();

//        highest(dishes, 3).map(Dish::getName).forEach(System.out::println);
//        slicingStreams(dishes);
//        flattenStreams();
        //reduceStreams(dishes);
        //pythagoreanNum();
        fib();
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
        System.out.println(chs);

        List<Integer> nums1 = Lists.newArrayList(1, 2, 3);
        List<Integer> nums2 = Lists.newArrayList(3, 4);
        nums1.stream()
                .flatMap(i -> nums2.stream()
                        .filter(j -> (j + i) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .forEach(p -> System.out.println(p[0] + " " + p[1]));
    }

    public static void reduceStreams(List<Dish> dishes) {

        int sum1 = IntStream.rangeClosed(0, 100).reduce(0, (a, b) -> a + b);
        int sum2 = IntStream.rangeClosed(0, 100).reduce(0, Integer::sum);
        int sum3 = IntStream.rangeClosed(0, 100).sum();
        OptionalInt sum4 = IntStream.rangeClosed(0, 100).reduce(Integer::sum);
        int products = IntStream.rangeClosed(1, 10)
                .reduce(1, (a, b) -> a * b);
        System.out.println(sum1);
        System.out.println(sum2);
        System.out.println(sum3);
        System.out.println(sum4);
        System.out.println(products);

        OptionalInt max = IntStream.rangeClosed(0, 100)
                            .reduce(Math::max);
                            //.reduce((a, b) -> a > b ? a : b);
        System.out.println(max);

        int count = (int) dishes.stream()
                            //.map(d -> 1).reduce(0, (a, b) -> a + b);
                            .count();
        System.out.println("dishes = " + count);
    }

    private static IntSupplier intSupplier = new IntSupplier() {
        private int prev = 0;
        private int curr = 1;
        @Override
        public int getAsInt() {
            int old = this.prev;
            int next = this.prev + this.curr;
            this.prev = this.curr;
            this.curr = next;
            return old;
        }
    };

    private static void fib() {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] +")"));
        System.out.println("-------------------------------------");
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(n -> n[0])
                .forEach(System.out::println);
        System.out.println("-------------------------------------");
        IntStream.generate(intSupplier).limit(20).forEach(System.out::println);
    }

    private static void pythagoreanNum() {
        IntStream.rangeClosed(1, 100).boxed().flatMap(a ->
                IntStream.rangeClosed(1, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b ->new int[]{a, b, (int)Math.sqrt(a * a + b * b)}))
                .limit(6).forEach(t -> System.out.println(t[0] + " " + t[1] + " " + t[2]));

        IntStream.rangeClosed(1, 100).boxed().flatMap(a ->
                IntStream.rangeClosed(a, 100)
                        .mapToObj(b ->new double[]{a, b, Math.sqrt(a * a + b * b)})).filter(t -> t[2] % 1 == 0)
                .limit(6).forEach(t -> System.out.println(t[0] + " " + t[1] + " " + t[2]));
    }

}
