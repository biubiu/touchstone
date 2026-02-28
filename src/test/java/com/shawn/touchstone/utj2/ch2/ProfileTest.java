package com.shawn.touchstone.utj2.ch2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {

    @Test
    public void matchAnswerFalseWhenMustMatchCriteriaNotMet() {
        Profile profile = new Profile("Bull Hockey .Inc");
        Question question = new BooleanQuestion(1, "Get bonus?");
        Answer profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);

        Criteria criteria = new Criteria();
        Answer criteriaAnswer = new Answer(question, Bool.TRUE);
        Criterion criterion = new Criterion(criteriaAnswer, Weight.MustMatch);
        criteria.add(criterion);

        MatchSet matchSet = profile.getMatchSet(criteria);
        assertFalse(matchSet.matches());
    }


}