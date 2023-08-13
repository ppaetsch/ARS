package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import com.uni.ARS.cards.*;
import com.uni.ARS.user.User;

import java.util.*;

public class ARSSession {

    private Integer id;

    public Admin admin;
    public String name;
    public List<QACard> cards;
    public Map<Integer, User> users = new HashMap<>();
    public DataHandler dataHandler;
    public SessionState state;

    public ARSSession() {
    }

    public ARSSession(Admin admin, String name) {
        this.admin = admin;
        cards = new ArrayList<>();
        this.name = name;
        dataHandler = new DataHandler();
        state = SessionState.START;
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

    public SessionState getState() {
        return state;
    }

    public void setState(SessionState state) {
        this.state = state;
    }

    /**
     * Creates new question and adds question id to user
     *
     * @param userId id of user
     * @param username name of user
     * @param question question asked by user
     */
    public void setQuestion(Integer userId, String username, String question){
        Question question1 = new Question(question, username);
        Integer id = dataHandler.insertQuestion(question1, name);
        users.get(userId).getQuestions().add(id);
    }

    public Question getQuestion(Integer userId){
        User user = users.get(userId);
        Question question = dataHandler.getQuestionForUser(user);
        return question;
    }

    public List<QACard> getAllCards(){
        return dataHandler.getAllCardsAsList();
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>(users.values());
        return userList;
    }

    /**
     * Creates new answer and adds answer and question id to user
     *
     * @param userId id of user
     * @param qid id of answered question
     * @param answer answer given by user
     */
    public void setAnswer(Integer userId, Integer qid, String answer, Integer difficulty){
        User user = users.get(userId);
        Answer answer1 = new Answer(answer, user.getName(), difficulty);
        Integer id = dataHandler.insertAnswer(answer1, qid);
        users.get(userId).getAnswers().put(id, qid);
    }

    public QACard getDataForEvaluation(Integer userId) {
        User user = users.get(userId);
        QACard qaCard = dataHandler.getDataForEvaluation(user);
        return qaCard;
    }

    /**
     * Creates new evaluation for answer and question and adds ids to user
     *
     * @param userId id of user
     * @param questionId id of evaluated question
     * @param answerId id of evaluated answer
     * @param evalq evaluation of question from user
     * @param evala evaluation of answer from user
     */
    public void setEvaluation(Integer userId, Integer questionId, Integer answerId, String evalq, String evala, Integer difficultyQuestion, Integer difficultyAnswer) {
        User user = users.get(userId);
        Evaluation answerEvaluation = new Evaluation(evala, user.getName(), difficultyAnswer);
        Integer idAnswer = dataHandler.insertEvaluationAnswer(answerEvaluation, questionId, answerId);
        users.get(userId).getEvaluationsAnswers().put(idAnswer, answerId);

        Evaluation questionEvaluation = new Evaluation(evalq, user.getName(), difficultyQuestion);
        Integer idQuestion = dataHandler.insertEvaluationQuestion(questionEvaluation, questionId);
        users.get(userId).getEvaluationsQuestions().put(idQuestion, questionId);
    }

    public void changeState(){
        state = state.getNextState();
    }

    public List<QACard> getCardsForUser(Integer userId) {
        return dataHandler.getCardsForUser(users.get(userId));
    }
}
