package com.uni.ARS.cards;

public abstract class CardComponent {

    private Integer id;
    private String value;
    private String user;

    public CardComponent(String value, String user) {
        this.value = value;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
