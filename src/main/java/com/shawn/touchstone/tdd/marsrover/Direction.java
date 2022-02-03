package com.shawn.touchstone.tdd.marsrover;

import com.google.common.collect.ImmutableMap;

public enum Direction {
    NORTH( 'N'),
    EAST( 'E'),
    SOUTH( 'S'),
    WEST('W'),
    NONE( ' ');

    private char direction;

    private static ImmutableMap<Character, Direction> map = ImmutableMap.of('N', Direction.NORTH,
            'E', Direction.EAST, 'W', Direction.WEST, 'S', Direction.SOUTH);

    Direction(char direction) {
        this.direction = direction;
    }

    public Direction get(char ch) {
        return map.getOrDefault(ch, NONE);
    }

    public Direction turnLeft() {
        int index = (this.ordinal() + 3 ) % 4;
        return Direction.values()[index];
    }

    public Direction turnRight() {
        int index = (this.ordinal() + 1) % 4;
        return Direction.values()[index];
    }
}
