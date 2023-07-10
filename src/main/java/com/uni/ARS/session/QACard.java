package com.uni.ARS.session;

import java.util.List;

public class QACard {

    private int id;
    private String question;
    private String questionUser;
    private List<String> answers;
    private List<String> answersUser;
    private List<String> evaluations;
    private List<String> evaluationsUser;

    public QACard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionUser() {
        return questionUser;
    }

    public void setQuestionUser(String questionUser) {
        this.questionUser = questionUser;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getAnswersUser() {
        return answersUser;
    }

    public void setAnswersUser(List<String> answersUser) {
        this.answersUser = answersUser;
    }

    public List<String> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<String> evaluations) {
        this.evaluations = evaluations;
    }

    public List<String> getEvaluationsUser() {
        return evaluationsUser;
    }

    public void setEvaluationsUser(List<String> evaluationsUser) {
        this.evaluationsUser = evaluationsUser;
    }
}
