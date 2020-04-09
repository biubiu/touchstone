package com.shawn.touchstone.statemachine;

public abstract class AbstractMarioStateMachine {

    protected int score;
    protected State curState;

    public AbstractMarioStateMachine() {
        init();
    }

    private void init() {
        curState = State.SMALL;
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public State getCurState() {
        return curState;
    }

    public abstract void obtainMushroom();

    public abstract void obtainCape();

    public abstract void obtainFireFlower();

    public abstract void meetMonster();
}
