package com.uni.ARS.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {

    private Integer id;
    private String name;
    private Set<Integer> questions = new HashSet<>();
    private Map<Integer, Integer> answers = new HashMap<>();
    private Map<Integer, Integer> evaluationsAnswers = new HashMap<>();
    private Map<Integer, Integer> evaluationsQuestions = new HashMap<>();

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Integer> questions) {
        this.questions = questions;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, Integer> answers) {
        this.answers = answers;
    }

    public Map<Integer, Integer> getEvaluationsAnswers() {
        return evaluationsAnswers;
    }

    public void setEvaluationsAnswers(Map<Integer, Integer> evaluationsAnswers) {
        this.evaluationsAnswers = evaluationsAnswers;
    }

    public Map<Integer, Integer> getEvaluationsQuestions() {
        return evaluationsQuestions;
    }

    public void setEvaluationsQuestions(Map<Integer, Integer> evaluationsQuestions) {
        this.evaluationsQuestions = evaluationsQuestions;
    }
}
