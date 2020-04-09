package com.shawn.touchstone.statemachine.statepattern;

import com.shawn.touchstone.statemachine.State;

public class CapeMario implements IMario {

    private static final CapeMario instance = new CapeMario();

    private CapeMario(){}
    public static CapeMario getInstance() {
        return instance;
    }

    @Override
    public State getName() {
        return State.SUPER;
    }

    @Override
    public void obtainMushroom(MarioStateMachine stateMachine) {

    }

    @Override
    public void obtainCape(MarioStateMachine stateMachine) {

    }

    @Override
    public void obtainFireFlower(MarioStateMachine stateMachine) {

    }

    @Override
    public void meetMonster(MarioStateMachine stateMachine) {

    }
}
