package com.uni.ARS.admin;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "admins")
public class Admin {

    @Id
    private String name;

    private String password;
    private Set<String> sessions = new HashSet<>();

    public Admin(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getSessions() {
        return sessions;
    }

    public void setSessions(Set<String> sessions) {
        this.sessions = sessions;
    }
}
