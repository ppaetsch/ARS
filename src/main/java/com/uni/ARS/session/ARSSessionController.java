package com.uni.ARS.session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ARSSessionController {

    @PostMapping("/Session/admin/{sessionname}")
    public String postSession(@PathVariable String sessionname){
        System.out.println("Session " + sessionname + " gestartet");
        return "session.html";
    }
}
