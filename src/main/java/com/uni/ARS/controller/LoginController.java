package com.uni.ARS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @PostMapping(value = "/login")
    public String postLogin(@RequestParam(name="name") String name, @RequestParam(name="pwd") String pwd, Model model){
        System.out.println("Name: " + name + " pwd: " + pwd);
        model.addAttribute("name", name);
        return "login.html";
    }

    @GetMapping(value = "/register")
    public String getRegister(){
        return "register.html";
    }

    @PostMapping(value = "/register")
    public String postRegister(@RequestParam(name="name") String name, @RequestParam(name="pwd") String pwd, Model model){
        System.out.println("Name: " + name + " pwd: " + pwd);
        model.addAttribute("name", name);
        model.addAttribute("add",true);
        return "index.html";
    }
}
