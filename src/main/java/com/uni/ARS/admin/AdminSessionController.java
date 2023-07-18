package com.uni.ARS.admin;

import com.uni.ARS.cards.QACard;
import com.uni.ARS.session.ARSSessionHandler;
import com.uni.ARS.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminSessionController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    @PostMapping(value="/getUserList")
    public String getUserList(@RequestParam (name="sessionname") String sessionname, Model model){
        List<User> users = arsSessionHandler.getSession(sessionname).getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("sessionname", sessionname);
        return "adminsession.html";
    }

    @PostMapping(value="/getQuestions")
    public String getNumberQuestions(@RequestParam (name="sessionname") String sessionname, Model model){
        List<QACard> cards = arsSessionHandler.getSession(sessionname).getAllCards();
        System.out.println("Länge liste: " + cards.size());
        model.addAttribute("cards", cards);
        model.addAttribute("sessionname", sessionname);
        return "adminsession.html";
    }
}
