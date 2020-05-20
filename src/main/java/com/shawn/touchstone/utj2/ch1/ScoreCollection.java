package com.shawn.touchstone.utj2.ch1;

import java.util.ArrayList;
import java.util.List;

public class ScoreCollection {
    private List<Scoreable> scores = new ArrayList<>();

    public void add(Scoreable scoreable) {
        if (scoreable == null) throw new IllegalArgumentException();
        scores.add(scoreable);
    }

    public int arithmeticMean(){
        return scores.stream().mapToInt(Scoreable::getScore).sum() / scores.size();
    }
}
