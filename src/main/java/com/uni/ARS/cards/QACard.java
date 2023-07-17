package com.uni.ARS.cards;

import java.util.ArrayList;
import java.util.List;

public class QACard {

    private Integer id;
    private Question question;
    private List<Answer> answerEvaluationList = new ArrayList<>();
    private String arsSession;

    public QACard(Question question, Integer id, String arsSession) {
        this.question = question;
        this.id = id;
        this.arsSession = arsSession;
        answerEvaluationList = new ArrayList<>();
    }

    public QACard(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswerEvaluationList() {
        return answerEvaluationList;
    }

    public void setAnswerEvaluationList(List<Answer> answerEvaluationList) {
        this.answerEvaluationList = answerEvaluationList;
    }

    public void addToAnswerEvaluationList(Answer answerEvaluation){
        answerEvaluationList.add(answerEvaluation);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArsSession() {
        return arsSession;
    }

    public void setArsSession(String arsSession) {
        this.arsSession = arsSession;
    }
}
