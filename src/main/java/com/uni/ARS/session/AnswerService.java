package com.uni.ARS.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    public String getQuestion(String sessionname, String username){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        System.out.println("Question Service: session " + arsSession.getName());
        //arsSession.setQuestion(username);
        return "";
    }


    @Async
    public void handleAnswer(String sessionname, String username, String question){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        System.out.println("Question Service: session " + arsSession.getName());
        //arsSession.setQuestion(username, question);
    }

}
