package com.shawn.touchstone.boss;


public class BooleanQuestion extends Question {

    private int id;

    public BooleanQuestion(int id, String text) {
        super(text);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
