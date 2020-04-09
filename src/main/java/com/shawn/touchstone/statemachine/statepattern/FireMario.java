package com.shawn.touchstone.statemachine.statepattern;

import com.shawn.touchstone.statemachine.State;

public class FireMario implements IMario {

    private static final FireMario instance = new FireMario();

    private FireMario(){}
    public static FireMario getInstance() {
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
