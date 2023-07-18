package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.Question;

import java.util.ArrayList;
import java.util.List;

public class ARSSession {

    private Integer id;

    public Admin admin;
    public String name;
    public List<QACard> cards;
    public List<String> users = new ArrayList<>();
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

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void setQuestion(String username, String question){
        Question question1 = new Question(question, username, this);
        dataHandler.insertQuestion(question1, name);
        System.out.println("Hinzufügen erfolgreich Session2");
    }

    public List<QACard> getAllCards(){
        return dataHandler.getAllCardsAsList();
    }
}
