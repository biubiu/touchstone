package com.shawn.touchstone.functional.patterns;


import java.util.ArrayList;
import java.util.List;

public class ObserverExp {

    public static void main(String[] args) {
        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObservers("The queen said her favourite book is Modern Java in Action!");

        // use lambda to replace observer impl
        f.registerObserver((tweet) -> {
            if (tweet != null && ((String) tweet).contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });

        f.registerObserver(tweet -> {
            if (tweet != null && ((String) tweet).contains("queen")) {
                System.out.println("Yet more news from London... " + tweet);
            }
        });
    }

    interface Observer<T> {
        void notify(T t);
    }

    interface Subject {
        void registerObserver(Observer o);

        void notifyObservers(String tweet);
    }

    static class NYTimes implements Observer<String> {
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        }
    }

    static class Guardian implements Observer<String> {
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet more news from London... " + tweet);
            }
        }
    }

    static class LeMonde implements Observer<String> {
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Today cheese, wine and news! " + tweet);
            }
        }
    }

    static class Feed implements Subject {
        private final List<Observer> observers = new ArrayList<>();

        public void registerObserver(Observer o) {
            this.observers.add(o);
        }

        public void notifyObservers(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }
}
