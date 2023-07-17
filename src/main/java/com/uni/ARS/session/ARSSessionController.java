package com.uni.ARS.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ARSSessionController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    @PostMapping("/Session/admin/{sessionname}")
    public String postSession(@PathVariable String sessionname){
        System.out.println("Session " + sessionname + " gestartet");
        return "adminsession.html";
    }

    @PostMapping("/Session/admin/{sessionname}/start")
    public String postSessions(@PathVariable String sessionname, Model model){
        String user = arsSessionHandler.getSession(sessionname).getUsers().get(0);
        model.addAttribute("user", user);
        return "adminsession.html";
    }
}
