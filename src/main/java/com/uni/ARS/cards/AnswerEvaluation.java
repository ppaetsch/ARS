package com.uni.ARS.cards;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class AnswerEvaluation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answer;
    private String answerUser;
    private String evaluation;
    private String evaluationUser;
    @ManyToOne
    private Question question;

    public AnswerEvaluation() {
    }

    public AnswerEvaluation(String answer, String answerUser, String evaluation, String evaluationUser, Question question) {
        this.answer = answer;
        this.answerUser = answerUser;
        this.evaluation = evaluation;
        this.evaluationUser = evaluationUser;
        this.question = question;
    }

    public Integer getId() {
        return id;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
