package com.shawn.touchstone.observers;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankAccountObTest {

    @Test
    public void testBankAccountChangeConsolidatorShouldReceiveChange() {
        BankAccount a = new BankAccount(10);
        BankAccount b = new BankAccount(10);
        Consolidator consolidator = new Consolidator(Lists.newArrayList(a, b));
        a.addObserver(consolidator);
        b.addObserver(consolidator);
        a.deposit(10);
        b.withdraw(5);
        assertEquals(25, consolidator.total());
    }

    @Test
    public void testEventBusBankAccountChangeConsolidatorReceiveChange() {
        EventBus eventBus = new EventBus();
        BankAccount a = new BankAccount(10);
        BankAccount b = new BankAccount(10);
        Consolidator consolidator = new Consolidator(Lists.newArrayList(a, b));
        eventBus.register(consolidator);
        a.deposit(20);
        eventBus.post(a);
        System.out.println(consolidator.total());
    }
}
