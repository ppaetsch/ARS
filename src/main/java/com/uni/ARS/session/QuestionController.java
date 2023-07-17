package com.uni.ARS.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @PostMapping("/Session/{sessionname}/userQuestion")
    public String getQuestion(@PathVariable String sessionname, @RequestParam (name="name") String name, @RequestParam (name="question") String question, Model model){
        System.out.println("sess: " + sessionname + " user: " + name + " q: " + question);
        questionService.handleQuestion(sessionname, name, question);
        return "userquestion.html";
    }
}
