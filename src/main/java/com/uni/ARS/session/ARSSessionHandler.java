package com.uni.ARS.session;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ARSSessionHandler {

    Map<String, ARSSession> arsSessions = new HashMap<>();

    public ARSSessionHandler() {
    }

    public Map<String, ARSSession> getArsSessions() {
        return arsSessions;
    }

    public void setArsSessions(Map<String, ARSSession> arsSessions) {
        this.arsSessions = arsSessions;
    }

    public ARSSession getSession(String name){
        return arsSessions.get(name);
    }

    public void addSession(ARSSession session){
        arsSessions.put(session.getName(), session);
    }
}
