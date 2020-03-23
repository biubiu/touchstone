package com.shawn.touchstone.concurrent.util;

import com.google.common.collect.Lists;

import java.util.List;
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
            for (int i = 0; i < threadCounts; i++) {
                if (i == threadCounts - 1) {
                    subSums.add(executor.submit(new SubTask(list.subList(i * len, list.size()))));
                } else {
                    subSums.add(executor.submit(new SubTask(list.subList(i * len, len * (i + 1) > list.size() ? list.size() : len * ( i + 1)))));
                }
            }

        }
    }
}
