package com.uni.ARS.session;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sessions")
public class Session {

    private String name;
    private String admin;

    public Session() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
