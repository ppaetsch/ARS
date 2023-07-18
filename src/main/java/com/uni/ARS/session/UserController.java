package com.uni.ARS.session;

import com.uni.ARS.user.User;
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

    @Autowired
    private UserService userService;

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
        User user = userService.addUser(name, sessionname);
        model.addAttribute("user", user.getName());
        model.addAttribute("userId", user.getId());
        model.addAttribute("sessionname", sessionname);
        return "userquestion.html";
    }



}
