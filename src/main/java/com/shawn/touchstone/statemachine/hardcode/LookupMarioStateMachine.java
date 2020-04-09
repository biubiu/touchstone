package com.shawn.touchstone.statemachine.hardcode;

import com.shawn.touchstone.statemachine.AbstractMarioStateMachine;
import com.shawn.touchstone.statemachine.State;

public class LookupMarioStateMachine extends AbstractMarioStateMachine {

    private static final State[][] transitionTable = {
            {State.SUPER, State.CAPE, State.FIRE, State.SMALL},
            {State.SUPER, State.CAPE, State.FIRE, State.SMALL},
            {State.CAPE, State.CAPE, State.CAPE, State.SMALL},
            {State.FIRE, State.FIRE, State.FIRE, State.SMALL}
    };

    private static final int[][] actionTable = {
            {+100, +200, +300, +0},
            {+0,   +200, +300, -100},
            {+0,   +0,   +0,   -200},
            {+0,   +0,   +0,   -300}
    };

    private void executeEvent(Event event) {
        int stateVal = curState.getVal();
        int eventVal = event.getVal();
        this.curState = transitionTable[stateVal][eventVal];
        this.score = actionTable[stateVal][eventVal];
    }
    @Override
    public void obtainMushroom() {
        executeEvent(Event.GOT_MUSHROOM);
    }

    @Override
    public void obtainCape() {
        executeEvent(Event.GOT_CAPE);
    }

    @Override
    public void obtainFireFlower() {
        executeEvent(Event.GOT_FIRE);
    }

    @Override
    public void meetMonster() {
        executeEvent(Event.MET_MONSTER);
    }

    public enum Event {
        GOT_MUSHROOM(0),
        GOT_CAPE(1),
        GOT_FIRE(2),
        MET_MONSTER(3);

        private int val;

        private Event(int val) {
            this.val = val;
        }

        public int getVal() {
            return this.val;
        }
    }

}
