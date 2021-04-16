package com.shawn.touchstone.functional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MoreStreamOpsExp {

    public static void main(String[] args) {
        List<Transaction>  transactions = Transaction.gen();

        transactions.stream().filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue)).forEach(System.out::println);
        transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(System.out::println);
        transactions.stream().map(Transaction::getTrader)
                            .filter(t -> t.getCity().equals("Cambridge")).distinct()
                            .sorted(Comparator.comparing(Transaction.Trader::getName)).forEach(System.out::println);
        System.out.println(transactions.stream().map(t -> t.getTrader().getName()).distinct().sorted()
                            .reduce("", String::concat));
        transactions.stream().map(Transaction::getValue)
                            .reduce(Math::max);
                            //.max(Integer::max);
        transactions.stream().min(Comparator.comparing(Transaction::getValue));
    }

}
