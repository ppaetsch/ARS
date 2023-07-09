package com.uni.ARS.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainMenuController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/MainMenu")
    public String getMainMenu(){
        System.out.println("Start Main Menu");
        return "";
    }

    @PostMapping("/MainMenu/session")
    public String getSession(){
        return "session.html";
    }


    @PostMapping("/MainMenu/data")
    public String getData(){
        return "data.html";
    }

    @PostMapping("/MainMenu/settings")
    public String getSettings(){
            return "settings.html";
    }

    @PostMapping("/MainMenu/logout")
    public String getLogout(){
        return "index.html";
    }

    @PostMapping("/password")
    public String changePassword(@RequestParam(name="name") String name, @RequestParam(name="oldpwd") String oldpwd, @RequestParam(name="newpwd") String newpwd, Model model){
        if (oldpwd.equals(newpwd)){
            model.addAttribute("failed",true);
            return "settings.html";
        }
        else {
            Admin admin = adminRepository.findByName(name);
            if (oldpwd.equals(admin.getPassword())){
                admin.setPassword(newpwd);
                Admin adminObj = adminRepository.save(admin);
                return "mainmenu.html";
            }
            else {
                model.addAttribute("failed2",true);
                return "settings.html";
            }

        }
    }
}
