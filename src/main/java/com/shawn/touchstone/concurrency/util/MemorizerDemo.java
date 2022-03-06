package com.shawn.touchstone.concurrency.util;

import org.apache.commons.lang3.concurrent.Computable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MemorizerDemo {

    //poor performance whith mutex exclusion
    private static class Memoizer1<A, V> implements Computable<A, V> {
        private final Map<A, V> cache = new HashMap<>();
        private final Computable<A, V> c;

        public Memoizer1(Computable<A, V> c) {
            this.c = c;
        }

        @Override
        public V compute(A arg) throws InterruptedException {
            synchronized (cache) {
                V result = cache.get(arg);
                if (result == null) {
                    result = c.compute(arg);
                    cache.put(arg, result);
                }
                return result;
            }
        }
    }

    // when expensive computation kicks off, other threads are not aware of the computation on going and
    //could init the same computation
    private static class Memoizer2<A, V> implements Computable<A, V> {
        private final Map<A, V> cache = new ConcurrentHashMap<>();
        private final Computable<A, V> c;

        public Memoizer2(Computable<A, V> c) {
            this.c = c;
        }

        @Override
        public V compute(A arg) throws InterruptedException {
                V result = cache.get(arg);
                if (result == null) {
                    result = c.compute(arg);
                    cache.put(arg, result);
                }
                return result;
        }
    }

    private static class Memoizer3<A, V> implements Computable<A, V> {
        private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
        private final Computable<A, V> c;

        public Memoizer3(Computable<A, V> c) {
            this.c = c;
        }

        @Override
        public V compute(A arg) throws InterruptedException {
            Future<V> f = cache.get(arg);
            // the if is not atomic, there is a small chance two threads both read null and starting the execution
            if (f == null) {
                Callable<V> eval = () -> c.compute(arg);
                FutureTask<V> ft = new FutureTask<>(eval);
                cache.put(arg, ft);
                f = ft;
                ft.run();
            }

            try {
                return f.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    private static class Memoizer4<A, V> implements Computable<A, V> {
        private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
        private final Computable<A, V> c;

        public Memoizer4(Computable<A, V> c) {
            this.c = c;
        }

        @Override
        public V compute(A arg) throws InterruptedException {
            Future<V> f = cache.get(arg);
            // the if is not atomic, there is a small chance two threads both read null and starting the execution
            if (f == null) {
                Callable<V> eval = () -> c.compute(arg);
                FutureTask<V> ft = new FutureTask<>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }

            try {
                return f.get();
            }catch (CancellationException e) {
                cache.remove(arg, f);
                throw new RuntimeException();
            } catch (ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
