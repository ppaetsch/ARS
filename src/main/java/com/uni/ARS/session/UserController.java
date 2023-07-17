package com.uni.ARS.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    @GetMapping("/Session/{sessionname}")
    public String getUserLogin(@PathVariable String sessionname, Model model){
        if(arsSessionHandler.getArsSessions().containsKey(sessionname)){
            model.addAttribute("sessionname", sessionname);
            return "userlogin.html";
        }
        return "error.html";
    }

    @PostMapping("/Session/userlogin")
    public String acceptUserLogin(@RequestParam(name="name") String name, @RequestParam(name="sessionname") String sessionname, Model model){
        ARSSession arsSession2 = arsSessionHandler.getSession(sessionname);
        arsSession2.users.add(name);
        model.addAttribute("user", name);
        model.addAttribute("sessionname", sessionname);
        return "userquestion.html";
    }

    @PostMapping("/Session/openAnswer")
    public String openAnswer(@RequestParam(name="name") String name, Model model){
        model.addAttribute("user", name);
        return "useranswer.html";
    }

    @GetMapping("/Session/openAnswer")
    public String openGetAnswer(){
        return "useranswer.html";
    }

}
