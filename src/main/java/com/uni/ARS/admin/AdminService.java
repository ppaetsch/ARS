package com.uni.ARS.admin;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public Admin registerAdmin(String name, String password){
        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(password);
        return admin;
    }
}
