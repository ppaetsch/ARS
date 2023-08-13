package com.uni.ARS.session;

import com.uni.ARS.cards.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    public Question getQuestion(String sessionname, Integer userId){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        Question question = arsSession.getQuestion(userId);
        return question;
    }


    //@Async
    public void handleAnswer(String sessionname, Integer userId, Integer qid, String answer){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        arsSession.setAnswer(userId, qid,answer);
    }

}
