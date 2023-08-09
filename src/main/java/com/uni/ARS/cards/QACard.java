package com.uni.ARS.cards;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "cardsDB")
public class QACard {

    private Integer cardId;
    private Question question;
    private Map<Integer, Evaluation> questionEvaluations = new HashMap<>();
    private Map<Integer, Answer> answerEvaluationMap = new HashMap<>();
    private String arsSession;

    public QACard(){

    }

    public QACard(Integer cardId, Question question, Map<Integer, Evaluation> questionEvaluations, Map<Integer, Answer> answerEvaluationMap, String arsSession) {
        this.cardId = cardId;
        this.question = question;
        this.questionEvaluations = questionEvaluations;
        this.answerEvaluationMap = answerEvaluationMap;
        this.arsSession = arsSession;
    }

    public QACard(Question question, Integer cardId, String arsSession) {
        this.question = question;
        this.cardId = cardId;
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
        return cardId;
    }

    public void setId(Integer cardId) {
        this.cardId = cardId;
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
