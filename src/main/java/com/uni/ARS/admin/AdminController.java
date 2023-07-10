package com.uni.ARS.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping(value = "/MainMenu", params = {"login"})
    public String postLogin(@RequestParam(name="name") String name, @RequestParam(name="pwd") String pwd, Model model){
        Admin admin = adminRepository.findByName(name);
        if(admin == null){
            model.addAttribute("name",name);
            model.addAttribute("failed",true);
            return "index.html";
        }
        else {
            if (name.equals(admin.getName())&& pwd.equals(admin.getPassword())){
                System.out.println("Name: " + admin.getName() + " pwd: " + admin.getPassword());
                model.addAttribute("name",name);
                return "mainmenu.html";
            }
            else {
                model.addAttribute("failed2", true);
                return "index.html";
            }

        }
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register.html";
    }

    @PostMapping("/register")
    public String addAdmin(@RequestParam(name="name") String name, @RequestParam(name="pwd") String pwd, Model model){
        Admin admin = adminRepository.findByName(name);
        if(admin == null){
            Admin admin1 = new Admin();
            admin1.setName(name);
            admin1.setPassword(pwd);
            Admin adminObj = adminRepository.save(admin1);
            model.addAttribute("name",adminObj.getName());
            model.addAttribute("add",true);
            return "index.html";
        }
        else {
            model.addAttribute("failed",true);
            return "register.html";
        }

    }

    @PostMapping("/password")
    public String changePassword(@RequestParam(name="name") String name, @RequestParam(name="oldpwd") String oldpwd, @RequestParam(name="newpwd") String newpwd, Model model){
        model.addAttribute("name",name);
        if (oldpwd.equals(newpwd)){
            model.addAttribute("failed",true);
            return "settings.html";
        }
        else {
            Admin admin = adminRepository.findByName(name);
            if (oldpwd.equals(admin.getPassword())){
                admin.setPassword(newpwd);
                Admin adminObj = adminRepository.save(admin);
                model.addAttribute("pwdchanged",true);
                return "settings.html";
            }
            else {
                model.addAttribute("failed2",true);
                return "settings.html";
            }

        }
    }
}
