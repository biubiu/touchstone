package com.shawn.touchstone.observers;

import java.util.Observable;

public class BankAccount extends Observable {

    private int balance = 0;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void deposit(Integer amount) {
        balance += amount;
        setChanged();
        notifyObservers();
    }

    public void withdraw(Integer amount) {
        if (amount < 0 || amount > balance) {
            throw new IllegalArgumentException();
        }
        balance = balance - amount;
        setChanged();
        notifyObservers();
    }

    public Integer curBalance() {
        return balance;
    }
}
