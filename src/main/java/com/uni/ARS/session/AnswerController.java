package com.uni.ARS.session;

import com.uni.ARS.cards.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * Recieves request to open useranswer.html
     *
     * @param sessionname name of currently used session
     * @param name name of user
     * @param userId id of user
     * @param model model to add attributes to html response
     * @return useranswer.html with unanswered question, error message if not available
     */
    @PostMapping("/Session/{sessionname}/openAnswer")
    public String openAnswer(@PathVariable String sessionname, @RequestParam (name="id") Integer userId, @RequestParam(name="name") String name, Model model){
        Question question = answerService.getQuestion(sessionname, userId);
        if (question==null){
            model.addAttribute("error", true);
        }
        else{
            model.addAttribute("question", question.getValue());
            model.addAttribute("questionId", question.getId());
        }
        model.addAttribute("user", name);
        model.addAttribute("userId", userId);
        return "useranswer.html";
    }

    @GetMapping("/Session/openAnswer")
    public String openGetAnswer(){
        return "useranswer.html";
    }

    /**
     * Recieve answer from user
     *
     * @param sessionname name of currently used session
     * @param name name of user
     * @param answer question from user
     * @param userId id of user
     * @param model model to add attributes to html response
     * @return useranswer.html
     */
    @PostMapping("/Session/{sessionname}/userAnswer")
    public String getAnswer(@PathVariable String sessionname,@RequestParam(name="name") String name, @RequestParam (name="questionId") Integer qid, @RequestParam (name="id") Integer userId, @RequestParam (name="answer") String answer, Model model){
        System.out.println("Der User mit ID " + userId + " hat für die Session " + sessionname + " zur Frage " + qid + " die Antwort " + answer + " abgegeben.");
        answerService.handleAnswer(sessionname, userId,qid,answer);
        Question question = answerService.getQuestion(sessionname, userId);
        if (question==null){
            model.addAttribute("error", true);
        }
        else{
            model.addAttribute("question", question.getValue());
            model.addAttribute("questionId", question.getId());
        }
        model.addAttribute("user", name);
        model.addAttribute("userId", userId);
        return "useranswer.html";
    }
}
