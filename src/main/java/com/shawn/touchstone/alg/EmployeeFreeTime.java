package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class EmployeeFreeTime {

    public static List<Interval> findEmployeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> freeTime = new ArrayList<>();
        PriorityQueue<EmployeeTime> minHeap = new PriorityQueue<>(Comparator.comparingInt(em -> em.interval().start()));
        for (int i = 0; i < schedule.size(); i++) {
            minHeap.offer(new EmployeeTime(schedule.get(i).get(0), i, 0));
        }
        Interval prev = minHeap.peek().interval();
        while (!minHeap.isEmpty()) {
            EmployeeTime top = minHeap.poll();
            if (prev.end() < top.interval().start()) {
                freeTime.add(new Interval(prev.end(), top.interval().start()));
                prev = top.interval();
            } else {
                if (prev.end() < top.interval().end()) {
                    prev = top.interval();
                }
            }
            List<Interval> employeeSchedule = schedule.get(top.employeeIdx());
            if (employeeSchedule.size() > top.intervalIdx() + 1) {
                minHeap.offer(new EmployeeTime(employeeSchedule.get(top.intervalIdx() + 1), top.employeeIdx(),
                        top.intervalIdx() + 1));
            }
        }
        return freeTime;
    }
}


record EmployeeTime(Interval interval, int employeeIdx, int intervalIdx) {}
