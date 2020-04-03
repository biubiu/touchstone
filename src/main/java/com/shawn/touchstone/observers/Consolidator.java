package com.shawn.touchstone.observers;

import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Consolidator implements Observer {
    private int total;
    private List<BankAccount> accounts;

    public Consolidator(List<BankAccount> accounts) {
        this.accounts = accounts;
        total = compute();
    }

    private int compute() {
        return accounts.stream().map(BankAccount::curBalance).reduce(0, (a, b) -> a + b);
    }

    @Override
    public void update(Observable o, Object arg) {
       total =  compute();
    }

    @Subscribe
    public void update(Object obj) {
        total =  compute();
    }

    public int total() {
        return total;
    }
}
