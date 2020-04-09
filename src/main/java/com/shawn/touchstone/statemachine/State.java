package com.shawn.touchstone.statemachine;

public enum State {
    SMALL(0),
    SUPER(1),
    FIRE(2),
    CAPE(3);

    private int val;

    State(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
