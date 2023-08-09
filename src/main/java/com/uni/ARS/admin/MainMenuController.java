package com.uni.ARS.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.QACardRepository;
import com.uni.ARS.session.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class MainMenuController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private QACardRepository repo;

    private Json json = new Json();

    @GetMapping("/MainMenu")
    public String getMainMenu(){
        System.out.println("Start Main Menu");
        return "";
    }

    @PostMapping("/MainMenu/session")
    public String getSession(@RequestParam(name="sessionname") String sessionname, @RequestParam(name="name") String name, Model model) throws JsonProcessingException {
        Admin admin = adminRepository.findByName(name);
        System.out.println("Admin heißt: " + admin.getName());
        model.addAttribute("name", name);
        if(admin != null){
            if(!arsSessionHandler.getArsSessions().containsKey(sessionname)){
            //if(arsSessionRepository.findByName(sessionname)==null){
                //Überprüfen, dass Session auch noch nicht in Datenbank
                createSession(sessionname, admin);
                admin.getSessions().add(sessionname);
                adminRepository.save(admin);
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
    public String getData(@RequestParam(name="name") String name,Model model){
        Admin admin = adminRepository.findByName(name);
        Set<String> sessions = admin.getSessions();
        model.addAttribute("sessions",sessions);
        model.addAttribute("name",name);
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

    public void createSession(String sessionname, Admin admin) throws JsonProcessingException {
        ARSSession session = new ARSSession(admin, sessionname);
        //ARSSession arsObj = arsSessionRepository.save(session);
        arsSessionHandler.addSession(session);
        //List<QACard> cards = repo.findAll();
        //System.out.println("Leere Liste? " + cards.isEmpty());
        //session.setCards(cards);

        //session.setCards(cards);
        //List<QACard> cards2 = session.getAllCards();
        //System.out.println("Leere Liste2? " + cards2.isEmpty());
        //JsonNode node = json.toJson(cards2.get(0));
        //repo.save(cards2.get(0));
        //System.out.println("JSON: " + json.stringify(node));
    }
}
