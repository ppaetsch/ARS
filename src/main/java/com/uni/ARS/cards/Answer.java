package com.uni.ARS.cards;

import java.util.ArrayList;
import java.util.List;

public class Answer extends CardComponent{

    private List<Evaluation> evaluationList;
    private Integer difficulty;

    public Answer(String value, String user, Integer difficulty) {
        super(value, user);
        evaluationList = new ArrayList<>();
        this.difficulty = difficulty;
    }


    public List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public void setEvaluationList(List<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void addToEvaluationList(Evaluation evaluation){
        evaluationList.add(evaluation);
    }
}
