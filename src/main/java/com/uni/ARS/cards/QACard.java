package com.uni.ARS.cards;

import java.util.HashMap;
import java.util.Map;

public class QACard {

    private Integer id;
    private Question question;
    private Map<Integer, Evaluation> questionEvaluations = new HashMap<>();
    private Map<Integer, Answer> answerEvaluationMap = new HashMap<>();
    private String arsSession;

    public QACard(Question question, Integer id, String arsSession) {
        this.question = question;
        this.id = id;
        this.arsSession = arsSession;
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

    public Map<Integer, Evaluation> getQuestionEvaluations() {
        return questionEvaluations;
    }

    public void setQuestionEvaluations(Map<Integer, Evaluation> questionEvaluations) {
        this.questionEvaluations = questionEvaluations;
    }

    public void addToQuestionEvaluationMap(Evaluation evaluation){
        questionEvaluations.put(evaluation.getId(), evaluation);
    }

    public Map<Integer, Answer> getAnswerEvaluationMap() {
        return answerEvaluationMap;
    }

    public void setAnswerEvaluationMap(Map<Integer, Answer> answerEvaluationMap) {
        this.answerEvaluationMap = answerEvaluationMap;
    }
}
