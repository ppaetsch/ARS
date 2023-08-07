package com.uni.ARS.cards;

import java.util.ArrayList;
import java.util.List;

public class Answer extends CardComponent{

    private List<Evaluation> evaluationList;

    public Answer(String value, String user) {
        super(value, user);
        evaluationList = new ArrayList<>();
    }


    public List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public void setEvaluationList(List<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
    }

    public void addToEvaluationList(Evaluation evaluation){
        evaluationList.add(evaluation);
    }
}
