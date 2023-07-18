package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.Question;
import com.uni.ARS.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println("Hinzufügen erfolgreich Session2");
    }

    public String getQuestion(String username){
        return "";
    }

    public List<QACard> getAllCards(){
        return dataHandler.getAllCardsAsList();
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>(users.values());
        return userList;
    }
}
