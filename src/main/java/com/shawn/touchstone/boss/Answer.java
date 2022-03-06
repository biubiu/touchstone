package com.shawn.touchstone.boss;

public record Answer(Question question, Bool answer) {

    public boolean match(Answer answer) {
        return answer != null && answer.answer.equals(this.answer);
    }
}


