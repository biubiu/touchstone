package com.shawn.touchstone.utj2.ch12;

public class Answer {
    private Question question;
    private Bool answer;

    public Answer(Question question, Bool answer) {
        this.question = question;
        this.answer = answer;
    }

    public boolean match(Answer answer) {
        return answer != null && answer.answer.equals(this.answer);
    }

    public Question getQuestion() {
        return this.question;
    }

    public Bool getAnswer() {
        return answer;
    }
}
