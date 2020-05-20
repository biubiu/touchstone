package com.shawn.touchstone.utj2.ch12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Criteria implements Iterable<Criterion>{

    private List<Criterion> criterions;

    public Criteria() {
        criterions = new ArrayList<>();
    }

    public void add(Criterion criterion) {
        criterions.add(criterion);
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criterions.iterator();
    }
}
