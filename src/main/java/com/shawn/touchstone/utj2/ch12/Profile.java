package com.shawn.touchstone.utj2.ch12;

import java.util.HashMap;
import java.util.Map;

public class Profile {

    private Map<String, Answer> answers = new HashMap<>();

    public boolean matches(Criteria criteria) {
        if (answers.isEmpty()) {
            return false;
        }
        boolean result = false;
        for (Criterion criterion : criteria) {
            Answer answer = getMatchingProfileAnswer(criterion);
            boolean match = criterion.getAnswer().match(answer);
            if (criterion.getWeight() == Weight.MustMatch && !match) {
                return false;
            } else if (criterion.getWeight() == Weight.DontCare && !match) {
                match = true;
            }
            result |= match;
        }

        return result;
    }

    private Answer getMatchingProfileAnswer(Criterion criterion) {
        String text = criterion.getAnswer().getQuestion().getText();
        return answers.get(text);
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestion().getText(), answer);
    }
}
