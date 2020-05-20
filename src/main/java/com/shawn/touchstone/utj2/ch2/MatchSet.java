package com.shawn.touchstone.utj2.ch2;

import java.util.HashMap;
import java.util.Map;

public class MatchSet {
    private Map<String, Answer> answers;
    private int score;
    private Criteria criteria;

    public MatchSet(Map<String, Answer> answers, Criteria criteria) {
        this.answers = answers;
        this.criteria = criteria;
    }

    private void calculateScore(Criteria criteria) {
        score = 0;
        for (Criterion criterion : criteria) {
            if (criterion.matches(answerMatching(criterion))) {
                score += criterion.getWeight().getValue();
            }
        }
    }

    public int getScore() {
        return score;
    }

    public boolean matches() {
        if (doesNotMeetAnyMustCriterion()) {
            return false;
        }
        return anyMatches();
    }

    private boolean anyMatches() {
        boolean anyMatches = false;
        for (Criterion criterion : criteria) {
            boolean match = criterion.matches(answerMatching(criterion));
            anyMatches |= match;
        }
        return anyMatches;
    }

    private boolean doesNotMeetAnyMustCriterion() {
        for (Criterion criterion : criteria) {
            boolean match = criterion.matches(answerMatching(criterion));
            if (!match && criterion.getWeight() == Weight.MustMatch) {
                return true;
            }
            return false;
        }
        return false;
    }


    private Answer answerMatching(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }
}
