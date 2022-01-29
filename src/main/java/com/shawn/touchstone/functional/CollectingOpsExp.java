package com.shawn.touchstone.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class CollectingOpsExp {

    public static void main(String[] args) {
        Arrays.stream(new String[]{}).collect(groupingBy(x -> x, counting()));
        List<Dish> menu = Dish.gen();
        List<int[]> result = new ArrayList<>();
        result.toArray(new int[0][0]);
//        statistics(menu);
//        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
//        System.out.println(shortMenu);
//        group(menu);
        multiLevelGroup(menu);
//        collectInSubgroups(menu);
//        partitioningExp(menu);
    }

    private static void group(List<Dish> menu) {
        Map<Dish.Type, List<Dish>> grouping = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(grouping);

        Map<Dish.Type, List<Dish>> de = menu.stream().filter(d -> d.getCalories() > 500).collect(groupingBy(Dish::getType));
        System.out.println("grouping = " + de);
        Map<Dish.Type, List<Dish>> caloricDishesByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                filtering(dish -> dish.getCalories() > 500, toList())));
        System.out.println(caloricDishesByType);

        Map<Dish.Type, List<String>> dishNameByType = menu.stream()
                            .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println(dishNameByType);

        Map<String, List<String>> tags = Dish.tags();
        Map<Dish.Type, Set<String>> dishNamesByType =
               menu.stream().collect(groupingBy(Dish::getType,
                       flatMapping(d -> tags.get(d.getName()).stream(),
                               toSet())));
        System.out.println(dishNamesByType);
    }

    private static void multiLevelGroup(List<Dish> menu) {
        Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream().collect(groupingBy(Dish::getType,
                        groupingBy(d -> {
                            if (d.getCalories() <= 400) return Dish.CaloricLevel.DIET;
                            else if (d.getCalories() <= 600) return Dish.CaloricLevel.NORMAL;
                            else return Dish.CaloricLevel.FAT;
                        })));
        System.out.println("dishesByTypeCaloricLevel = " + dishesByTypeCaloricLevel);

        Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> dishesByCaloricLevel = 
                menu.stream().collect(groupingBy(Dish::getType, groupingBy(Dish::getCaloricLevel)));
        System.out.println("dishesByCaloricLevel = " + dishesByCaloricLevel);

        Map<Dish.Type, Dish> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType,
                collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("mostCaloricByType = " + mostCaloricByType);
    }

    private static void collectInSubgroups(List<Dish> menu) {
        Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println("typesCount = " + typesCount);

        Map<Dish.Type, Optional<Dish>> mostCaloricByOptionalType = menu.stream().collect(groupingBy(Dish::getType,
                maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println("mostCaloricByOptionalType = " + mostCaloricByOptionalType);
        Map<Dish.Type, Dish> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType,
                collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("mostCaloricByType = " + mostCaloricByType);

        Map<Dish.Type, Set<Dish.CaloricLevel>> caloricLevelsByType = menu.stream().collect(groupingBy(Dish::getType,
                mapping(d -> {
                    if (d.getCalories() <= 400) return Dish.CaloricLevel.DIET;
                    else if (d.getCalories() <= 700) return Dish.CaloricLevel.NORMAL;
                    else return Dish.CaloricLevel.FAT;
                //}, toSet())));
                }, toCollection(HashSet::new))));
        System.out.println("caloricLevelsByType = " + caloricLevelsByType);
    }

    private static void partitioningExp(List<Dish> menu) {
        Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("partitionedMenu = " + partitionedMenu);

        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println("vegetarianDishesByType = " + vegetarianDishesByType);
        Map<Boolean, List<Integer>> primes = partitionPrimes(100);
        System.out.println("primes = " + primes);
    }

    private static void statistics(List<Dish> menu) {
        //return menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        IntSummaryStatistics statistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println("statistics = " + statistics);
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(
                partitioningBy(candidate -> isPrime(candidate)));
    }

    private static boolean isPrime(int candidate) {
        int[] nums = new int[]{};
        IntStream.range(2, 2 + 3).mapToObj(n -> nums[n]).collect(toList());
        return IntStream.range(2, candidate).noneMatch(i -> candidate % 2 == 0);
    }

    private static long simpleCount(List<Dish> menu) {
        //return menu.stream().collect(Collectors.counting());
        return menu.stream().count();
    }

    private static long total(List<Dish> menu) {
        return menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    private static Optional<Dish> highest(List<Dish> menu) {
        return menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
    }

    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num: arr) freq.put(num, freq.getOrDefault(num, 0) + 1);
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        for (Map.Entry<Integer, Integer> en: freq.entrySet()) minHeap.offer(en);
        for (int i = 0; i < k; i++) {
            Map.Entry<Integer, Integer> curr = minHeap.poll();
            if (curr.getValue() - 1 > 0) {
                curr.setValue(curr.getValue() - 1);
                minHeap.offer(curr);
            }
        }
        return minHeap.size();
    }
}
