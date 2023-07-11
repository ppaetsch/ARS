package com.uni.ARS.cards;

import com.uni.ARS.session.ARSSession;
import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String question;
    private String questionUser;
    @ManyToOne
    private ARSSession arsSession;

    public Question() {
    }

    public Question(String question, String questionUser, ARSSession arsSession) {
        this.question = question;
        this.questionUser = questionUser;
        this.arsSession = arsSession;
    }

    public Integer getId() {
        return id;
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

    public ARSSession getArsSession() {
        return arsSession;
    }

    public void setArsSession(ARSSession arsSession) {
        this.arsSession = arsSession;
    }
}
