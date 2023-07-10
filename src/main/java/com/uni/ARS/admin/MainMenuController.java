package com.uni.ARS.admin;

import com.uni.ARS.session.ARSSession;
import com.uni.ARS.session.ARSSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainMenuController {

    @Autowired
    private ARSSessionRepository arsSessionRepository;

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
        model.addAttribute("name", name);
        if(admin != null){
            if(arsSessionRepository.findByName(sessionname)==null){
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
    public String getData(){
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
        ARSSession arsObj = arsSessionRepository.save(session);

    }
}
