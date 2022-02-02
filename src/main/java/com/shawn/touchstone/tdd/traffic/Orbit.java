package com.shawn.touchstone.tdd.traffic;

public record Orbit(int distance, double craters, int maxSpeed) {
    public Orbit {
        assert distance > 0;
        assert maxSpeed > 0;
        assert craters >= 0;
    }
}
