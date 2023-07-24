package com.uni.ARS.session;

import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    public QACard getDataForEvaluation(String sessionname, Integer userId){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        System.out.println("Question Service: session " + arsSession.getName());
        QACard qaCard = arsSession.getDataForEvaluation(userId);
        System.out.println("Size of answer list " + qaCard.getAnswerEvaluationMap().size() + "in Service");
        //arsSession.setQuestion(username);
        return qaCard;
    }

    @Async
    public void handleEvaluations(String sessionname, Integer userId, Integer questionId, Integer answerId, String evalq, String evala) {
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        System.out.println("Eval Service: session " + arsSession.getName());
        arsSession.setEvaluation(userId, questionId,answerId, evalq, evala);
    }
}
