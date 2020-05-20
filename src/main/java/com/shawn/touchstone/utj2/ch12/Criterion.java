package com.shawn.touchstone.utj2.ch12;

public class Criterion{

    private Answer answer;
    private Weight weight;

    public Criterion(Answer answer, Weight weight) {
        this.answer = answer;
        this.weight = weight;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Weight getWeight() {
        return weight;
    }
}
