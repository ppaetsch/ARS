package com.uni.ARS.cards;

public class Evaluation {

    private Integer id;
    private String evaluation;
    private String evaluationUser;

    public Evaluation(Integer id, String evaluation, String evaluationUser) {
        this.id = id;
        this.evaluation = evaluation;
        this.evaluationUser = evaluationUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getEvaluationUser() {
        return evaluationUser;
    }

    public void setEvaluationUser(String evaluationUser) {
        this.evaluationUser = evaluationUser;
    }
}
