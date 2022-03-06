package com.shawn.touchstone.boss;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThrows;

public class AssertTest {
    @Test
    public void comparesArraysFailing() {
        assertThat(new String[]{"a", "b", "c"}, equalTo(new String[]{"a", "b", "c"}));
    }

    @Test
    public void matchesFailure() {
        assertThat("xyz", startsWith("xyz"));
    }

    @Test
    public void variousMatcherTests() {
        String name = "my big fat acct";
        assertThat(name, is(equalTo("my big fat acct")));
        assertThat(name, not(equalTo("my big fat cct")));
        assertThat(name, is(not(equalTo("my big fat cct"))));
        assertThat(name, is(notNullValue()));
    }

    @Test
    public void assertDoubleCloseTo() {
        assertThat(2.32 * 3, closeTo(6.96, 0.0005));
    }


    @Test(expected = NullPointerException.class)
    public void throwsWhenWithdrawingTooMuch() {
        Objects.requireNonNull(null);
    }

    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    class Account {
        int balance;
        String name;

        Account(String name) {
            this.name = name;
        }

        void withdraw(int dollars) {
            if (balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }
    }

    private Account account;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionRule() {
        account = new Account("an account name");
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("balance only 0");
        account.withdraw(100);
    }

    @Test
    public void useAssertThrows() {
        account = new Account("an account name");
        InsufficientFundsException exp = assertThrows(InsufficientFundsException.class, () -> account.withdraw(100));
        assertThat(exp.getMessage(), is("balance only 0"));
    }

    @Test
    public void testTime() {
        Instant now = Instant.now();
        TestTime testTime = new TestTime();
        testTime.setClock(Clock.fixed(now, ZoneId.systemDefault()));
        assertThat(testTime.getTime(), is(equalTo(now)) );
    }

    class TestTime {
        private Clock clock = Clock.systemDefaultZone();

        public void setClock(Clock clock) {
            this.clock = clock;
        }

        public Instant getTime() {
            return clock.instant();
        }
    }
}
