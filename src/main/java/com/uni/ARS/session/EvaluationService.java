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
        QACard qaCard = arsSession.getDataForEvaluation(userId);
        return qaCard;
    }

    //@Async
    public void handleEvaluations(String sessionname, Integer userId, Integer questionId, Integer answerId, String evalq, String evala) {
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        arsSession.setEvaluation(userId, questionId,answerId, evalq, evala);
    }
}
