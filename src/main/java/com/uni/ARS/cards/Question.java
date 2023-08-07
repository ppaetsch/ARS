package com.uni.ARS.cards;

import com.uni.ARS.session.ARSSession;

public class Question extends CardComponent {

    private ARSSession arsSession; //wahrscheinlich überflüssig

    public Question(String value, String user, ARSSession arsSession) {
        super(value, user);
        this.arsSession = arsSession;
    }

    public ARSSession getArsSession() {
        return arsSession;
    }

    public void setArsSession(ARSSession arsSession) {
        this.arsSession = arsSession;
    }
}
