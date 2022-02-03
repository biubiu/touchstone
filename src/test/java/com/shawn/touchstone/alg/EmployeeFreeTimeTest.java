package com.shawn.touchstone.alg;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

public class EmployeeFreeTimeTest {

    @Test
    public void givenTwoEmployeeSchedulesShouldReturnFreeTime() {

        List<Interval> first = List.of(new Interval(1, 3), new Interval(9, 12));
        List<Interval> second = List.of(new Interval(2, 4), new Interval(6, 8));
        List<List<Interval>> employees = List.of(first, second);
        List<Interval> freeTime = EmployeeFreeTime.findEmployeeFreeTime(employees);

        assertEquals(2, freeTime.size());
        assertThat(freeTime, hasItems(new Interval(4, 6), new Interval(8, 9)));
    }

}