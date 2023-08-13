package com.uni.ARS.cards;

public class Evaluation extends CardComponent{

    private Integer difficulty;

    public Evaluation(String value, String user, Integer difficulty) {
        super(value, user);
        this.difficulty = difficulty;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
}
