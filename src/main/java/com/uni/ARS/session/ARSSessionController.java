package com.uni.ARS.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ARSSessionController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    Logger logger = LoggerFactory.getLogger(ARSSessionController.class);

    @PostMapping("/Session/admin/{sessionname}")
    public String postSession(@PathVariable String sessionname){
        logger.trace("Session " + sessionname + " started");
        arsSessionHandler.getSession(sessionname).changeState();
        return "adminsession.html";
    }

}
