package com.shawn.touchstone.functional;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Lambda {

    enum Operation {
        PLUS ("+", (a, b) -> a + b),
        MINUS("-", (a, b) -> a - b);

        private final String operation;
        private final DoubleBinaryOperator op;
        private static final Map<String, Operation> stringToEnum =
                Stream.of(values()).collect(toMap(Object::toString, e -> e));
        Operation(String operation, DoubleBinaryOperator op) {
            this.operation = operation;
            this.op = op;
        }

        public double apply(double x, double y) {
            return op.applyAsDouble(x, y);
        }
    }

    public void freqMapping(File file) {
        Map<String, Long> freq = new HashMap<>();
        try(Stream<String> words = new Scanner(file).delimiter().splitAsStream("")) {
//            words.forEach(word -> {
//                freq.merge(word.toLowerCase(), 1L, Long::sum);
//            });
        freq = words.collect(groupingBy(String::toLowerCase, counting()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> topTen = freq.keySet().stream().sorted(Comparator.comparing(freq::get).reversed()).limit(10)
                .collect(Collectors.toList());
    }

    public void mergeMap() {
        List<String> arr = Lists.newArrayList("anc", "cna", "nnn");
        arr.stream().collect(toMap(str -> str, val -> 1, Integer::sum, TreeMap::new)).forEach((k, v) -> System.out.println(k + " " + v));
        arr.stream().collect(groupingBy(w -> alphabetize(w))).forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println(arr.stream().collect(Collectors.joining(",")));
    }

    public void anagrams() {
        IntStream.range(1, 20).collect(StringBuilder::new, (sb, c) -> sb.append(c), StringBuilder::append).toString();
        try (Stream<String> words = Files.lines(Paths.get(""))) {
            words.collect(groupingBy(word -> alphabetize(word))).values().stream().forEach(g -> System.out.println(g.size() + " " + g));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String alphabetize(String word) {
        char[] a = word.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }

    public static <E> Stream<List<E>> of(List<E> list) {
        return Stream.concat(Stream.of(Collections.emptyList()), prefixes(list).flatMap(l -> suffixes(l)));
    }

    private static <E> Stream<List<E>> prefixes(List<E> list) {
        return IntStream.rangeClosed(1, list.size()).mapToObj(end -> list.subList(0, end));
    }

    private static <E> Stream<List<E>> suffixes(List<E> list) {
        return IntStream.rangeClosed(0, list.size()).mapToObj(start -> list.subList(start, list.size()));
    }
    public static void main(String[] args) {
        new Lambda().mergeMap();
    }
}
