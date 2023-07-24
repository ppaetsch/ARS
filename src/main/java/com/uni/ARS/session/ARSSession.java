package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import com.uni.ARS.cards.Answer;
import com.uni.ARS.cards.Evaluation;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.Question;
import com.uni.ARS.user.User;

import java.util.*;

public class ARSSession {

    private Integer id;

    public Admin admin;
    public String name;
    public List<QACard> cards;
    public Map<Integer, User> users = new HashMap<>();
    public DataHandler dataHandler;

    public ARSSession() {
    }

    public ARSSession(Admin admin, String name) {
        this.admin = admin;
        cards = new ArrayList<>();
        this.name = name;
        dataHandler = new DataHandler();
        preloadData();
    }

    public Integer getId() {
        return id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QACard> getCards() {
        return cards;
    }

    public void setCards(List<QACard> cards) {
        this.cards = cards;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Map<Integer, User> getUserList() {
        return users;
    }

    public void setUserList(Map<Integer, User> users) {
        this.users = users;
    }

    public void setQuestion(Integer userId, String username, String question){
        Question question1 = new Question(question, username, this);
        Integer id = dataHandler.insertQuestion(question1, name);
        users.get(userId).getQuestions().add(id);
        System.out.println("Hinzufügen erfolgreich Session");
    }

    public Question getQuestion(String username){
        Question question = dataHandler.getQuestionForUser(username);
        return question;
    }

    public List<QACard> getAllCards(){
        return dataHandler.getAllCardsAsList();
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>(users.values());
        return userList;
    }

    public void setAnswer(Integer userId, Integer qid, String answer){
        User user = users.get(userId);
        Answer answer1 = new Answer(answer, user.getName());
        Integer id = dataHandler.insertAnswer(answer1, qid);
        users.get(userId).getAnswers().put(id, qid);
        System.out.println("Antwort Hinzufügen erfolgreich Session");
    }

    public QACard getDataForEvaluation(Integer userId) {
        User user = users.get(userId);
        QACard qaCard = dataHandler.getDataForEvaluation(userId);
        System.out.println("Size of answer list " + qaCard.getAnswerEvaluationMap().size() + "in ARS");
        return qaCard;
    }

    public void setEvaluation(Integer userId, Integer questionId, Integer answerId, String evalq, String evala) {
        User user = users.get(userId);
        Evaluation answerEvaluation = new Evaluation(evala, user.getName());
        Integer idAnswer = dataHandler.insertEvaluationAnswer(answerEvaluation, questionId, answerId);
        users.get(userId).getEvaluationsAnswers().put(idAnswer, answerId);
        System.out.println("evala in ARS Session");

        Evaluation questionEvaluation = new Evaluation(evalq, user.getName());
        Integer idQuestion = dataHandler.insertEvaluationQuestion(questionEvaluation, questionId);
        users.get(userId).getEvaluationsQuestions().put(idQuestion, questionId);
        System.out.println("evalq in ARS Session");
    }

    public void preloadData(){
        Random rand = new Random();
        for (Integer i = 5; i < 11; i++){
            users.put(i,new User(i, "User: " + i));
        }
        for (Integer j = 0; j<5; j++){
            Integer u = rand.nextInt(users.size());
            u=u+5;
            setQuestion(u, users.get(u).getName(), "question: " + j);
        }
        for (Integer k = 0; k<10; k++){
            Integer u = rand.nextInt(users.size());
            u=u+5;
            Integer q = rand.nextInt(5);
            setAnswer(u,q,"answer: " + k);
        }
    }
}
