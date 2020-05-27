package com.shawn.touchstone.tdd.marsrover;

import java.util.List;
import java.util.Objects;

public class Planet {

    private Point max;

    private List<Point> obstacles;

    public Planet(Point max, List<Point> obstacles) {
        this.max = max;
        this.obstacles = obstacles;
    }

    public Point getMax() {
        return max;
    }

    public List<Point> getObstacles() {
        return obstacles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return max.equals(planet.max) &&
                obstacles.equals(planet.obstacles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(max, obstacles);
    }
}
