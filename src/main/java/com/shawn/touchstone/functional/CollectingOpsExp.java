package com.shawn.touchstone.functional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class CollectingOpsExp {

    public static void main(String[] args) {
        List<Dish> dishes = Dish.gen();
        System.out.println(statistics(dishes));
        String shortMenu = dishes.stream().map(Dish::getName).collect(Collectors.joining(","));
        System.out.println(shortMenu);
        //group(dishes);
        //multiLevelGroup(dishes);
        collectInSubgroups(dishes);
    }

    private static void group(List<Dish> menu) {
        Map<Dish.Type, List<Dish>> grouping = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(grouping);
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
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                flatMapping(dish -> tags.get( dish.getName() ).stream(),
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

        Map<Dish.Type, Dish> mostCaloricByType = menu.stream().collect(groupingBy(Dish::getType,
                collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println("mostCaloricByType = " + mostCaloricByType);
    }

    private static void collectInSubgroups(List<Dish> menu) {
        Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println("typesCount = " + typesCount);
    }
    private static IntSummaryStatistics statistics(List<Dish> menu) {
        //return menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        return menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
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
}
