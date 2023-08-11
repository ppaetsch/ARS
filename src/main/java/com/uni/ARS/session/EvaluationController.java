package com.uni.ARS.session;


import com.uni.ARS.cards.QACard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/Session/{sessionname}/openEval")
    public String openEvaluation(@PathVariable String sessionname, @RequestParam(name="id") Integer userId, @RequestParam(name="name") String name, Model model){
        System.out.println("openEval Started");
        System.out.println("openEval Session: " + sessionname);

        QACard qaCard = evaluationService.getDataForEvaluation(sessionname,userId);
        if (qaCard==null){
            model.addAttribute("error", true);
        }
        else{
            System.out.println("Size of answer list " + qaCard.getAnswerEvaluationMap().size() + "in Controller");
            System.out.println("openEval got QACard");
            System.out.println(qaCard.getAnswerEvaluationMap().keySet());
            model.addAttribute("question", qaCard.getQuestion().getValue());
            model.addAttribute("questionId", qaCard.getQuestion().getId());
            model.addAttribute("answer", qaCard.getAnswerEvaluationMap().get(0).getValue());
            model.addAttribute("answerId", qaCard.getAnswerEvaluationMap().get(0).getId());
        }
        model.addAttribute("user", name);
        model.addAttribute("userId", userId);
        System.out.println("openEval ending");
        return "usereval.html";
    }

    @PostMapping("/Session/{sessionname}/userEval")
    public String setEvaluations(@PathVariable String sessionname, @RequestParam(name="id") Integer userId, @RequestParam(name="questionId") Integer questionId, @RequestParam(name="answerId") Integer answerId, @RequestParam(name="evalq") String evalq, @RequestParam(name="evala") String evala, Model model){
        System.out.println("Start insert Eval");
        evaluationService.handleEvaluations(sessionname, userId, questionId, answerId, evalq, evala);
        QACard qaCard = evaluationService.getDataForEvaluation(sessionname,userId);
        if (qaCard==null){
            model.addAttribute("error", true);
        }
        else {
            System.out.println(qaCard.getAnswerEvaluationMap().keySet());
            model.addAttribute("question", qaCard.getQuestion().getValue());
            model.addAttribute("questionId", qaCard.getQuestion().getId());
            model.addAttribute("answer", qaCard.getAnswerEvaluationMap().get(0).getValue());
            model.addAttribute("answerId", qaCard.getAnswerEvaluationMap().get(0).getId());
            model.addAttribute("userId", userId);
        }
        return "usereval.html";
    }

}
