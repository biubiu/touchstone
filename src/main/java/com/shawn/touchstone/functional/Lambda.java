package com.shawn.touchstone.functional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Lambda {

    enum Operation {
        PLUS ("+", (a, b) -> a + b),
        MINUS("-", (a, b) -> a - b);

        private final String operation;
        private final DoubleBinaryOperator op;

        Operation(String operation, DoubleBinaryOperator op) {
            this.operation = operation;
            this.op = op;
        }

        public double apply(double x, double y) {
            return op.applyAsDouble(x, y);
        }
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
}
