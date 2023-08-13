package com.uni.ARS.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * Gets admin name and password and checks with database, if correct
     *
     * @param name name of user
     * @param pwd password of user
     * @param model model to add attributes to html response
     * @return mainmenu.html if login successful, index.html otherwise
     */
    @PostMapping(value = "/MainMenu", params = {"login"})
    public String postLogin(@RequestParam(name="name") String name, @RequestParam(name="pwd") String pwd, Model model){
        Admin admin = adminRepository.findByName(name);
        if(admin == null){
            logger.warn("User login failed: User " + name + " not existing");
            model.addAttribute("name",name);
            model.addAttribute("failed",true);
            return "index.html";
        }
        else {
            if (name.equals(admin.getName())&& pwd.equals(admin.getPassword())){
                model.addAttribute("name",name);
                return "mainmenu.html";
            }
            else {
                logger.warn("User login failed: password incorrect. User: " + name);
                model.addAttribute("failed2", true);
                return "index.html";
            }

        }
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register.html";
    }

    /**
     * Registers admin and stores in database
     *
     * @param name name of user
     * @param pwd password of user
     * @param model model to add attributes to html response
     * @return index.html if register was successful, register.html otherwise
     */
    @PostMapping("/register")
    public String addAdmin(@RequestParam(name="name") String name, @RequestParam(name="pwd") String pwd, Model model){
        Admin admin = adminRepository.findByName(name);
        if(admin == null){
            logger.trace("User " + name + " register complete");
            Admin admin1 = adminService.registerAdmin(name, pwd);
            Admin adminObj = adminRepository.save(admin1);
            model.addAttribute("name",adminObj.getName());
            model.addAttribute("add",true);
            return "index.html";
        }
        else {
            logger.warn("Register failed: User already exists");
            model.addAttribute("failed",true);
            return "register.html";
        }
    }

    /**
     * Changes the password from old to new
     *
     * @param name name of user
     * @param oldpwd old password of user
     * @param newpwd new password of user
     * @param model model to add attributes to html response
     * @return settings.html with success notification if successful, fail notification otherwise
     */
    @PostMapping("/password")
    public String changePassword(@RequestParam(name="name") String name, @RequestParam(name="oldpwd") String oldpwd, @RequestParam(name="newpwd") String newpwd, Model model){
        model.addAttribute("name",name);
        if (oldpwd.equals(newpwd)){
            logger.warn("Password change for user " + name + " failed: old password is identical to new password");
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
                logger.warn("Password change for user " + name + " failed: old password is incorrect");
                model.addAttribute("failed2",true);
                return "settings.html";
            }

        }
    }
}
