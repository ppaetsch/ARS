package com.uni.ARS.session;

import com.uni.ARS.admin.Admin;
import com.uni.ARS.admin.AdminRepository;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.QACardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class DataController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private QACardRepository repo;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Recieves request to get all cards from session
     *
     * @param name name of admin
     * @param sessionname name of stored session
     * @param model model to add attributes to html response
     * @return data.html
     */
    @PostMapping("/Data/getData")
    public String getData(@RequestParam(name="name") String name, @RequestParam(name="sessionname") String sessionname, Model model){
        Admin admin = adminRepository.findByName(name);
        Set<String> sessions = admin.getSessions();
        model.addAttribute("sessions",sessions);
        model.addAttribute("name", name);
        model.addAttribute("sessionname",sessionname);
        if (sessions.contains(sessionname)){
            List<QACard> cards = repo.findByArsSession(sessionname);
            model.addAttribute("cards", cards);
            return "data.html";
        }
        return "data.html";
    }

    /**
     * Recieves request to delete stored cards and session from database
     *
     * @param name name of admin
     * @param sessionname name of stored session
     * @param model model to add attributes to html response
     * @return data.html
     */
    @PostMapping("/Data/deleteData")
    public String deleteData(@RequestParam(name="name") String name, @RequestParam(name="sessionname") String sessionname, Model model){
        Admin admin = adminRepository.findByName(name);
        Set<String> sessions = admin.getSessions();
        model.addAttribute("sessions",sessions);
        model.addAttribute("name", name);
        model.addAttribute("sessionname",sessionname);
        if (sessions.contains(sessionname)){
            repo.deleteByArsSession(sessionname);
            admin.getSessions().remove(sessionname);
            adminRepository.save(admin);
            sessionRepository.deleteByName(sessionname);
            return "data.html";
        }
        return "data.html";
    }
}
