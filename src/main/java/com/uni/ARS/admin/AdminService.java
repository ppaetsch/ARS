package com.uni.ARS.admin;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private Integer id = 0;

    public Admin registerAdmin(String name, String password){
        Admin admin = new Admin();
        admin.setId(id);
        admin.setName(name);
        admin.setPassword(password);
        id++;
        return admin;
    }
}
