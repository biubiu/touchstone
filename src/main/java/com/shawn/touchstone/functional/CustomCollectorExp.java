package com.shawn.touchstone.functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.partitioningBy;

public class CustomCollectorExp {
    public static void main(String[] args) {

    }

    private static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double)candidate);
//        return primes.stream().takeWhile(i -> i <= candidateRoot)
//                            .noneMatch(i -> candidate % i == 0);
        return takeWhile(primes, i -> i <= candidateRoot).stream().noneMatch(p -> candidate % p == 0);
    }

    private static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for(A a: list) {
            if (!p.test(a)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    private class PrimeNumCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<>() {
                {
                    put(true, new ArrayList<>());
                    put(false, new ArrayList<>());
                }
            };
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
                acc.get(isPrime(acc.get(true), candidate)).add(candidate);
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
                return map1;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
        }
    }
    private class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
            //return () -> new ArrayList<>();
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return List::add;
            //return (list, item) -> list.add(item);
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (l1, l2) -> {
                l1.addAll(l2);
                return l1;
            };
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return Function.identity();
            //return x -> x;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH,
                    CONCURRENT));
        }
    }
}
