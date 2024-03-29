package com.uni.ARS.user;

import com.uni.ARS.session.ARSSessionHandler;
import com.uni.ARS.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Returns login page for user to join session
     *
     * @param sessionname name of currently used session
     * @param model model to add attributes to html response
     * @return userlogin.html if session exist, error.html otherwise
     */
    @GetMapping("/Session/{sessionname}")
    public String getUserLogin(@PathVariable String sessionname, Model model){
        if(arsSessionHandler.getArsSessions().containsKey(sessionname)){
            if(arsSessionHandler.getSession(sessionname).getState().equals(SessionState.START)||arsSessionHandler.getSession(sessionname).getState().equals(SessionState.QUESTION)){
                model.addAttribute("sessionname", sessionname);
                return "userlogin.html";
            }
            logger.warn("Session " + sessionname +" already running, joining prohibited");
        }
        logger.warn("Session " + sessionname +" not existing");
        return "error.html";
    }

    /**
     * Logs in user with name to session
     *
     * @param name name of user
     * @param sessionname name of currently used session
     * @param model model to add attributes to html response
     * @return userquestion.html if login was successful, error.html otherwise
     */
    @PostMapping("/Session/userlogin")
    public String acceptUserLogin(@RequestParam(name="name") String name, @RequestParam(name="sessionname") String sessionname, Model model){
        User user = userService.addUser(name, sessionname);
        if (user == null){
            return "error.html";
        }
        model.addAttribute("user", user.getName());
        model.addAttribute("userId", user.getId());
        model.addAttribute("sessionname", sessionname);
        return "userquestion.html";
    }



}
