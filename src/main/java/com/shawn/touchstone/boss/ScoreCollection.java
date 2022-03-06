package com.shawn.touchstone.boss;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreCollection {
    private List<Scoreable> scores = new ArrayList<>();

    public void add(Scoreable scoreable) {
        if (scoreable == null) throw new IllegalArgumentException();
        scores.add(scoreable);
    }

    public int arithmeticMean() {
        if (scores.isEmpty()) return 0;
        int total = scores.stream().mapToInt(Scoreable::getScore).sum();
        return total / scores.size();
    }
}
