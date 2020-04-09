package com.shawn.touchstone.statemachine.statepattern;

import com.shawn.touchstone.statemachine.State;

public interface IMario {
    State getName();
    void obtainMushroom(MarioStateMachine stateMachine);
    void obtainCape(MarioStateMachine stateMachine);
    void obtainFireFlower(MarioStateMachine stateMachine);
    void meetMonster(MarioStateMachine stateMachine);
}
