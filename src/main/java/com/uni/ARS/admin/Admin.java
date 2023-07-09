package com.uni.ARS.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admins")
public class Admin {

    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String password;

    public Admin(){}

    public Integer getId() {
        return id;
    }

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

    /*
    @Override
    public int hashCode(){
        if(getId() != null){
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj) {
            return true;
        }
        if (obj==null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
    }*/
}
