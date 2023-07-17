package com.uni.ARS.admin;

import com.uni.ARS.session.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainMenuController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/MainMenu")
    public String getMainMenu(){
        System.out.println("Start Main Menu");
        return "";
    }

    @PostMapping("/MainMenu/session")
    public String getSession(@RequestParam(name="sessionname") String sessionname, @RequestParam(name="name") String name, Model model){
        Admin admin = adminRepository.findByName(name);
        System.out.println("Admin heißt: " + admin.getName());
        model.addAttribute("name", name);
        if(admin != null){
            if(!arsSessionHandler.getArsSessions().containsKey(sessionname)){
            //if(arsSessionRepository.findByName(sessionname)==null){
                createSession(sessionname, admin);
                model.addAttribute("sessionname", sessionname);
                return "session.html";
            }
            else {
                model.addAttribute("sessionfailed", true);
                return "mainmenu.html";
            }
        }
        else {
            System.out.println("Admin nicht gefunden");
            return "mainmenu.html";
        }
    }

    @PostMapping("/MainMenu/data")
    public String getData(Model model){
        /*List<String> l1 = new ArrayList<>();
        l1.add("Da");
        l1.add("Da1");
        List<String> l2 = new ArrayList<>();
        l2.add("Dort");
        l2.add("Dort1");
        l2.add("Dort2");
        QACard qaCard = new QACard("Wo?","Huber",l1, "Leo", "Gut", "Melanie");
        QACard qaCard2 = new QACard("Wo1?","Huber1",l2, "Leo1", "Gut1", "Melanie1");
        List<QACard> qaCardList = new ArrayList<>();
        qaCardList.add(qaCard);
        qaCardList.add(qaCard2);
        model.addAttribute("cards", qaCardList);*/
        return "data.html";
    }

    @PostMapping("/MainMenu/settings")
    public String getSettings(@RequestParam(name="name") String name, Model model){
        model.addAttribute("name",name);
        return "settings.html";
    }

    @PostMapping("/MainMenu/logout")
    public String getLogout(){
        return "redirect:/";
    }

    @PostMapping(value = "/MainMenu", params = {"settings"})
    public String getMainMenuFromSettings(@RequestParam(name="name") String name, Model model){
        model.addAttribute("name",name);
        return "mainmenu.html";
    }

    public void createSession(String sessionname, Admin admin){
        ARSSession session = new ARSSession(admin, sessionname);
        //ARSSession arsObj = arsSessionRepository.save(session);
        arsSessionHandler.addSession(session);
    }
}
