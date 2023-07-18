package com.uni.ARS.session;

import com.uni.ARS.cards.Answer;
import com.uni.ARS.cards.Evaluation;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHandler {

    private Map<Integer, Question> questions = new HashMap<>();
    private Map<Integer, Answer> answers = new HashMap<>();
    private Map<Integer, Evaluation> evaluations = new HashMap<>();
    private Map<Integer, Integer> questionToAnswers = new HashMap<>();
    private Map<Integer, Integer> answerToEvaluations = new HashMap<>();
    private Map<Integer, QACard> cards = new HashMap<>();
    private int nextQuestionId = 0;

    public DataHandler() {
    }

    public void insertQuestion(Question question, String sessionname){
        Integer id = nextQuestionId;
        question.setId(id);
        questions.put(id, question);
        QACard qaCardNew = new QACard(question, id, sessionname);
        cards.put(id, qaCardNew);
        System.out.println("Hinzufügen erfolgreich DataHandler");
        System.out.println("Länge von cards " + cards.size());
        nextQuestionId++;
    }

    public void updateQuestion(Question question){
        Integer id = question.getId();
        questions.replace(id, question);
    }

    public List<QACard> getAllCardsAsList(){
        return new ArrayList<QACard>(cards.values());
    }

}
