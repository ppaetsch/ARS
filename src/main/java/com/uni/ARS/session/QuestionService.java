package com.uni.ARS.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private ARSSessionHandler arsSessionHandler;


    @Async
    public void handleQuestion(String sessionname, String username, String question){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        System.out.println("Question Service: session " + arsSession.getName());
        arsSession.setQuestion(username, question);
    }
}
