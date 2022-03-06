package com.shawn.touchstone.boss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
    private final Map<String, Answer> answers;
    private int score;
    private String name;

    public Profile(String name) {
        this.name = name;
        this.answers = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.put(answer.question().getText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;
        boolean kill = false;
        boolean anyMatches = false;
        for (Criterion criterion : criteria) {
            Answer answer = answers.get(criterion.getAnswer().question().getText());
            boolean match = criterion.getWeight() == Weight.DontCare || answer.match(criterion.getAnswer());
            if (!match && criterion.getWeight() == Weight.MustMatch) {
                kill = true;
            }
            if (match) {
                score += criterion.getWeight().getValue();
            }
            anyMatches |= match;
        }
        if (kill) return false;
        return anyMatches;
    }

    public List<Answer> find(Predicate<Answer> pred) {
        return answers.values().stream()
                .filter(pred)
                .collect(Collectors.toList());
    }
}
