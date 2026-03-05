package com.shawn.touchstone.temporal.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class AccountStore {
    private static final AccountStore INSTANCE = new AccountStore();
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    private AccountStore() {}

    public static AccountStore getInstance() {
        return INSTANCE;
    }

    public void create(String email, String name) {
        accounts.put(email, new Account(email, name));
    }

    public Account get(String email) {
        return accounts.get(email);
    }

    public static class Account {
        private final String email;
        private final String name;

        public Account(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() { return email; }
        public String getName() { return name; }
    }
}
