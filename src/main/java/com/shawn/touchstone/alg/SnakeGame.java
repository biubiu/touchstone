package com.shawn.touchstone.alg;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

public class SnakeGame {

    private int width;
    private int height;
    private int[][] food;
    private int foodIdx;
    private int score;
    private LinkedList<Point> snake;
    private Set<Point> set;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        this.score = 0;
        this.snake = new LinkedList<>();
        Point start = new Point(0, 0);
        snake.add(start);
        this.foodIdx = 0;
        set = new HashSet<>();
        set.add(start);
    }

    public int move(String direction) {
        if (score == -1) {
            return - 1;
        }
        Point head = snake.peekFirst();
        int x = head.x;
        int y = head.y;
        switch (direction) {
            case "L":
                y--;
                break;
            case "R":
                y++;
                break;
            case "U":
                x--;
            case "D":
                x++;
        }
        Point newHead = new Point(x, y);
        Point tail = snake.peekLast();
        set.remove(tail);
        //check bounds and whether overlapped with itself
        if (x < 0 || x == height || y < 0 || y == height || set.contains(newHead)) {
            score = -1;
            return -1;
        }
        set.add(newHead);
        snake.offerFirst(newHead);

        if (foodIdx < food.length && x == food[foodIdx][0] && y == food[foodIdx][1]) {
            set.add(tail);
            score++;
            foodIdx++;
        } else {
            snake.pollLast();
        }
        return score;
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
