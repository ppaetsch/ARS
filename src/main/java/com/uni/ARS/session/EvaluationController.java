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

    /**
     * Recieves request to open evaluation
     *
     * @param sessionname name of currently used session
     * @param userId id of user
     * @param name name of user
     * @param model model to add attributes to html response
     * @return usereval.html with card if evaluation is available, error message otherwise
     */
    @PostMapping("/Session/{sessionname}/openEval")
    public String openEvaluation(@PathVariable String sessionname, @RequestParam(name="id") Integer userId, @RequestParam(name="name") String name, Model model){
        QACard qaCard = evaluationService.getDataForEvaluation(sessionname,userId);
        if (qaCard==null){
            model.addAttribute("error", true);
        }
        else{
            model.addAttribute("question", qaCard.getQuestion().getValue());
            model.addAttribute("questionId", qaCard.getQuestion().getId());
            model.addAttribute("answer", qaCard.getAnswerEvaluationMap().get(0).getValue());
            model.addAttribute("answerId", qaCard.getAnswerEvaluationMap().get(0).getId());
        }
        model.addAttribute("user", name);
        model.addAttribute("userId", userId);
        return "usereval.html";
    }

    /**
     * Recieves evaluation from user
     *
     * @param sessionname name of currently used session
     * @param userId id of user
     * @param questionId id of evaluated question
     * @param answerId id of evaluated answer
     * @param evalq evaluation for question
     * @param evala evaluation for answer
     * @param model model to add attributes to html response
     * @return usereval.html with card if evaluation is available, error message otherwise
     */
    @PostMapping("/Session/{sessionname}/userEval")
    public String setEvaluations(@PathVariable String sessionname, @RequestParam(name="id") Integer userId, @RequestParam(name="questionId") Integer questionId, @RequestParam(name="answerId") Integer answerId, @RequestParam(name="evalq") String evalq, @RequestParam(name="evala") String evala,@RequestParam(name="difficultyQuestion") Integer difficultyQuestion,@RequestParam(name="difficultyAnswer") Integer difficultyAnswer, Model model){
        evaluationService.handleEvaluations(sessionname, userId, questionId, answerId, evalq, evala, difficultyQuestion, difficultyAnswer);
        QACard qaCard = evaluationService.getDataForEvaluation(sessionname,userId);
        if (qaCard==null){
            model.addAttribute("error", true);
        }
        else {
            model.addAttribute("question", qaCard.getQuestion().getValue());
            model.addAttribute("questionId", qaCard.getQuestion().getId());
            model.addAttribute("answer", qaCard.getAnswerEvaluationMap().get(0).getValue());
            model.addAttribute("answerId", qaCard.getAnswerEvaluationMap().get(0).getId());
            model.addAttribute("userId", userId);
        }
        return "usereval.html";
    }

}
