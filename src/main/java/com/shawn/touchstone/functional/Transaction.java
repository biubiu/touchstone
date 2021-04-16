package com.shawn.touchstone.functional;

import java.util.Arrays;
import java.util.List;

class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return "{" + this.trader + ", " + "year: " + this.year + ", " +
                "value:" + this.value + "}";
    }

    static class Trader {
        private final String name;
        private final String city;

        public Trader(String n, String c) {
            this.name = n;
            this.city = c;
        }

        public String getName() {
            return this.name;
        }

        public String getCity() {
            return this.city;
        }

        public String toString() {
            return "Trader:" + this.name + " in " + this.city;
        }
    }

    public static List<Transaction> gen() {
        Transaction.Trader raoul = new Transaction.Trader("Raoul", "Cambridge");
        Transaction.Trader mario = new Transaction.Trader("Mario", "Milan");
        Transaction.Trader alan = new Transaction.Trader("Alan", "Cambridge");
        Transaction.Trader brian = new Transaction.Trader("Brian", "Cambridge");
        return Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }
}
