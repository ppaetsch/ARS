package com.uni.ARS.session;

import com.uni.ARS.cards.Answer;
import com.uni.ARS.cards.Evaluation;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.Question;
import com.uni.ARS.user.User;

import java.util.*;

public class DataHandler {

    private Map<Integer, Question> questions = new HashMap<>();
    private Map<Integer, Answer> answers = new HashMap<>();
    private Map<Integer, Evaluation> evaluationsAnswer = new HashMap<>();
    private Map<Integer, Evaluation> evaluationsQuestion = new HashMap<>();
    private Map<Integer, Integer> questionToAnswers = new HashMap<>();
    private Map<Integer, Integer> answerToEvaluations = new HashMap<>();
    private Map<Integer, QACard> cards = new HashMap<>();
    private int nextQuestionId = 0;
    private int nextAnswerId = 0;
    private int nextEvaluationAnswerId = 0;
    private int nextEvaluationQuestionId = 0;

    public DataHandler() {
    }

    public Integer insertQuestion(Question question, String sessionname){
        Integer id = nextQuestionId;
        question.setId(id);
        questions.put(id, question);
        QACard qaCardNew = new QACard(question, id, sessionname);
        cards.put(id, qaCardNew);
        System.out.println("Hinzufügen erfolgreich DataHandler");
        System.out.println("Länge von cards " + cards.size());
        nextQuestionId++;
        return id;
    }

    public void updateQuestion(Question question){
        Integer id = question.getId();
        questions.replace(id, question);
    }

    public List<QACard> getAllCardsAsList(){
        return new ArrayList<QACard>(cards.values());
    }

    public Question getQuestionForUser(User user) {
        Random random = new Random();
        int tries = 0;
        while(tries<30){
            Integer i = random.nextInt(questions.size());
            System.out.println("Tries: " + i);
            System.out.println("Contains: " + user.getQuestions().toString());
            System.out.println("Contains: " + user.getAnswers().values().toString());
            if(!user.getQuestions().contains(i)&&!user.getAnswers().values().contains(i)){
                Question question = questions.get(i);
                System.out.println(question == null);
                return question;
            }
            else {
                System.out.println("Try again");
            }
            tries++;
        }
        return null;
    }

    public Integer insertAnswer(Answer answer, Integer qid){
        Integer id = nextAnswerId;
        answer.setId(id);
        answers.put(id, answer);

        cards.get(qid).getAnswerEvaluationMap().put(answer.getId(),answer);

        questionToAnswers.put(id, qid);
        //cards.get(qid).addToAnswerEvaluationList(answer);
        System.out.println("Antwort inzufügen erfolgreich DataHandler");
        nextAnswerId++;
        return id;
    }

    public QACard getDataForEvaluation(User user) {
        if (questionToAnswers.size()!=0){
            Random random = new Random();
            int tries=0;
            while(tries<30){
                Integer i = random.nextInt(questionToAnswers.size());
                Integer qid = questionToAnswers.get(i);
                if(!user.getQuestions().contains(qid)&&!user.getAnswers().values().contains(qid)&&!user.getEvaluationsQuestions().values().contains(qid)&&!user.getAnswers().containsKey(i)&&!user.getEvaluationsAnswers().values().contains(i)){
                    QACard card = cards.get(qid);
                    QACard qaCard = new QACard(card.getQuestion());
                    Answer answer = card.getAnswerEvaluationMap().get(i);
                    qaCard.getAnswerEvaluationMap().put(0,answer);
                    return qaCard;
                }
                else {
                    System.out.println("Try again");
                }
                tries++;
            }
        }
        return null;
    }

    public Integer insertEvaluationAnswer(Evaluation answerEvaluation, Integer questionId, Integer answerId) {
        Integer id = nextEvaluationAnswerId;
        answerEvaluation.setId(id);
        evaluationsAnswer.put(id, answerEvaluation);
        cards.get(questionId).getAnswerEvaluationMap().get(answerId).addToEvaluationList(answerEvaluation);
        System.out.println("Evaluation Antwort inzufügen erfolgreich DataHandler");
        nextEvaluationAnswerId++;
        return id;
    }

    public Integer insertEvaluationQuestion(Evaluation questionEvaluation, Integer questionId){
        Integer id = nextEvaluationQuestionId;
        questionEvaluation.setId(id);
        evaluationsQuestion.put(id, questionEvaluation);
        cards.get(questionId).addToQuestionEvaluationMap(questionEvaluation);
        System.out.println("Evaluation Frage inzufügen erfolgreich DataHandler");
        nextEvaluationQuestionId++;
        return id;
    }

    public List<QACard> getCardsForUser(User user) {
        List<QACard> card = new ArrayList<>();
        for (Integer userQuestion:user.getQuestions()){
            card.add(cards.get(userQuestion));
        }
        List<Integer> userAnswers = new ArrayList<>(user.getAnswers().values());
        for (Integer userAnswer:userAnswers){
            card.add(cards.get(userAnswer));
        }
        return card;
    }
}
