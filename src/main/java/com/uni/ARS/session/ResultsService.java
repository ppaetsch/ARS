package com.uni.ARS.session;

import com.uni.ARS.cards.QACard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultsService {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    public List<QACard> getCardsForUser(String sessionname, Integer userId) {
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        return arsSession.getCardsForUser(userId);
    }
}
