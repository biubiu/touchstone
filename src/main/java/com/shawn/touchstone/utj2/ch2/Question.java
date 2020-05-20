package com.shawn.touchstone.utj2.ch2;

public abstract class Question {
    private String text;
    private String[] answerChoices;
    private int id;

    public Question(int id, String text, String[] answerChoices) {
        this.text = text;
        this.answerChoices = answerChoices;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public String getAnswerChoice(int i) {
        return answerChoices[i];
    }

    public boolean match(Answer answer) {
        return false;
    }

    public abstract boolean match(int expected, int actual);

    public int indexOf(String matchingAnsChoice) {
        for (int i = 0; i < answerChoices.length; i++) {
            if (answerChoices[i].equals(matchingAnsChoice)) return i;
        }
        return -1;
    }
}
