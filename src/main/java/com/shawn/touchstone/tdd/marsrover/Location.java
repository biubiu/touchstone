package com.shawn.touchstone.tdd.marsrover;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

public class Location {

    private Point point;

    private Direction direction;

    private static final int FORWARD = 1;

    private static final int BACKWARD = -1;

    public Location(Point p, Direction direction) {
        this.point = p;
        this.direction = direction;
    }

    public Point getPoint() {
        return point;
    }

    protected Direction getDirection() {
        return this.direction;
    }


    public void turnLeft() {
        this.direction = direction.turnLeft();
    }

    public void turnRight() {
        this.direction = direction.turnRight();
    }

    public boolean forward(Point max) {
        return forward(max, emptyList());
    }

    public boolean forward(Point max, List<Point> obstacles) {
        return move(FORWARD, max, obstacles);
    }

    public boolean move(int move, Point max, List<Point> obstacles) {
        int x = this.point.getX();
        int y = this.point.getY();
        switch (this.getDirection()) {
            case NORTH: y = wrap(y + move, max.getY()); break;
            case WEST: x = wrap(x - move, max.getX()); break;
            case SOUTH: y = wrap(y - move, max.getY()); break;
            case EAST: x = wrap(x + move, max.getX()); break;
        }
        point = new Point(x, y);
        boolean collided = collide(obstacles, point);
        return !collided;
    }

    private boolean collide(List<Point> obstacles, Point point) {
        return (obstacles.stream().anyMatch(p -> p.equals(point)));
    }

    public int wrap(int val, int max) {
        if (val < 0) {
            return 0;
        } else if (val > max){
            return max;
        } else {
            return val;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return point.equals(location.point) &&
                direction == location.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, direction);
    }

    public boolean backward(Point max) {
        return backward(max, emptyList());
    }

    public boolean backward(Point max, List<Point> obstacles) {
        return move(BACKWARD, max, obstacles);
    }
}
