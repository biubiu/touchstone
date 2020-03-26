package com.shawn.touchstone.concurrent.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CyclicBarrierDemo {


    private static class CountNumber {
        private long sum;
        private List<Integer> list;
        private int threadCounts;

        public CountNumber(List<Integer> list, int threadCounts) {
            this.list = list;
            this.threadCounts = threadCounts;
        }

        public long divideAndConquerSum() {
            ExecutorService executor = Executors.newFixedThreadPool(threadCounts);
            int len = list.size() / threadCounts;//split list
            if (len == 0) {
                threadCounts = list.size();
                len = list.size() / threadCounts;
            }
            List<Future<Long>> subSums = Lists.newArrayList();
            CompletionService<Long> completionService = new ExecutorCompletionService<>(executor);
            List<List<Integer>> subLists = Lists.partition(list, len);
            for (List<Integer> sub: subLists) {
                completionService.submit(() -> new SubTask<Long>(sub).call());
            }

            for (int i = 0; i < subLists.size(); i++) {

                try {
                    Future<Long> f = completionService.take();
                    Long sum = f.get();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e.getCause());
                }
            }
            throw new RuntimeException();
        }
    }

    private static class SubTask<Long> implements Callable<Long>{

        private List<Integer> subList;

        public SubTask(List<Integer> subList) {
            this.subList = subList;
        }

        @Override
        public Long call() throws Exception {
            return null;
        }
    }
}
