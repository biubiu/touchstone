package com.shawn.touchstone.statemachine.branches;

import com.shawn.touchstone.statemachine.AbstractMarioStateMachine;
import com.shawn.touchstone.statemachine.State;

public class BranchingMarioStateMachine extends AbstractMarioStateMachine {

    public void obtainMushroom() {
        if (curState.equals(State.SMALL)) {
            this.curState = State.SUPER;
            this.score += 100;
        }
    }

    public void obtainCape() {
        if (curState.equals(State.SMALL) || curState.equals(State.SUPER)) {
            this.curState = State.CAPE;
            this.score += 200;
        }
    }

    public void obtainFireFlower() {
        if (curState.equals(State.SMALL) || curState.equals(State.SUPER)) {
            this.curState = State.FIRE;
            this.score += 300;
        }
    }

    public void meetMonster() {
        if (curState.equals(State.SUPER)) {
            this.curState = State.SMALL;
            this.score -= 100;
            return;
        }
        if (curState.equals(State.CAPE)) {
            this.curState = State.SMALL;
            this.score -= 200;
            return;
        }
        if (curState.equals(State.FIRE)) {
            this.curState = State.SMALL;
            this.score -= 300;
            return;
        }
    }

}
