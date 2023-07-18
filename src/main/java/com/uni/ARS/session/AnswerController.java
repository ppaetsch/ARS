package com.uni.ARS.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnswerController {

    @Autowired
    private AnswerService answerService;

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
