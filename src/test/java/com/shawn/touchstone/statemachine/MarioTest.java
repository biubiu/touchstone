package com.shawn.touchstone.statemachine;

import com.shawn.touchstone.statemachine.branches.BranchingMarioStateMachine;
import com.shawn.touchstone.statemachine.hardcode.LookupMarioStateMachine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MarioTest {

    @Test
    public void testBranchesShouldChangeState() {
        AbstractMarioStateMachine mario = new BranchingMarioStateMachine();
        assertState(State.SMALL, 0, mario);
        mario.obtainMushroom();
        assertState(State.SUPER, 100, mario);
        mario.obtainCape();
        assertState(State.CAPE, 300, mario);
    }

    @Test
    public void testLookupShouldChangeState() {
        AbstractMarioStateMachine mario = new LookupMarioStateMachine();
        assertState(State.SMALL, 0, mario);
        mario.obtainMushroom();
        assertState(State.SUPER, 100, mario);
        mario.obtainCape();
        assertState(State.CAPE, 300, mario);
    }

    public void assertState(State expectedState, int expectedScore,
                            AbstractMarioStateMachine curMario) {
        assertEquals(expectedState, curMario.getCurState());
        assertEquals(expectedScore, curMario.getScore());
    }
}
