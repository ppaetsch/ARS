package com.uni.ARS.session;

public enum SessionState {
    START, QUESTION, ANSWER, EVALUATION, RESULTS;

    private static final SessionState[] states = values();

    public SessionState getNextState(){
        return states[(ordinal() + 1)% states.length];
    }
}
