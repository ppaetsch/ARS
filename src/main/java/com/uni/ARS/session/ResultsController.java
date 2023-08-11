package com.uni.ARS.session;

import com.uni.ARS.cards.QACard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ResultsController {

    @Autowired
    private ResultsService resultsService;

    /**
     * Gets all cards for user to display
     *
     * @param sessionname name of currently used session
     * @param userId id of user
     * @param name name of user
     * @param model model to add attributes to html response
     * @return userresults.html
     */
    @PostMapping("/Session/{sessionname}/openFinal")
    public String openAnswer(@PathVariable String sessionname, @RequestParam(name="id") Integer userId, @RequestParam(name="name") String name, Model model) {
        List<QACard> cards = resultsService.getCardsForUser(sessionname, userId);
        model.addAttribute("cards", cards);
        model.addAttribute("sessionname", sessionname);
        model.addAttribute("userId", userId);
        model.addAttribute("user", name);
        return "userresults.html";
    }

}
