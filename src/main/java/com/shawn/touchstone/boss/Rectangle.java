package com.shawn.touchstone.boss;

public class Rectangle {
    private Point first;
    private Point second;

    public Rectangle(Point first, Point second) {
        this.first = first;
        this.second = second;
    }

    public Point origin() {
        return first;
    }

    public Point opposite() {
        return null;
    }

    record Point(int x, int y) {}
}


