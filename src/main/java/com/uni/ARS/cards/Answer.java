package com.uni.ARS.cards;

import java.util.ArrayList;
import java.util.List;

public class Answer {

    private Integer id;
    private String answer;
    private String answerUser;
    private List<Evaluation> evaluationList;

    public Answer(String answer, String answerUser) {
        this.answer = answer;
        this.answerUser = answerUser;
        evaluationList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public List<Evaluation> getEvaluationList() {
        return evaluationList;
    }

    public void setEvaluationList(List<Evaluation> evaluationList) {
        this.evaluationList = evaluationList;
    }
}
