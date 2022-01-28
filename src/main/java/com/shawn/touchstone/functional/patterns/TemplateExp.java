package com.shawn.touchstone.functional.patterns;

import java.util.Map;
import java.util.function.Consumer;

public class TemplateExp {

    class Customer {
        Integer id;
        String name;

        public Customer(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    Map<Integer, Customer> customers = Map.of(1, new Customer(1, "a"),
            2, new Customer(2, "b"));

    abstract class OnlineBanking {

        public void processCustomer(int id) {
            Customer c = customers.get(id);
            makeCustomerHappy(c);
        }

        abstract void makeCustomerHappy(Customer c);
    }

    //with traditional template pattern
    public void processCustomer(int id) {
        new OnlineBanking() {
            @Override
            void makeCustomerHappy(Customer c) {
                //do something
            }
        }.processCustomer(id);
    }

    //with lambda
    private Consumer<Customer> makeCustomerHappy = customer -> {
        //do something;
    };

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        makeCustomerHappy.accept(customers.get(id));
    }
}
