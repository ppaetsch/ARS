package com.uni.ARS.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.uni.ARS.cards.QACardRepository;
import com.uni.ARS.session.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Controller
public class MainMenuController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private QACardRepository repo;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/MainMenu")
    public String getMainMenu(){
        System.out.println("Start Main Menu");
        return "";
    }

    @PostMapping("/MainMenu/session")
    public String getSession(@RequestParam(name="name") String name,Model model){
        model.addAttribute("name",name);
        return "sessionstart.html";
    }

    @PostMapping("/SessionCreate")
    public String createSession(@RequestParam(name="sessionname") String sessionname, @RequestParam(name="name") String name, Model model) throws IOException, WriterException {
        Admin admin = adminRepository.findByName(name);
        System.out.println("Admin heißt: " + admin.getName());
        model.addAttribute("name", name);
        if(admin != null){
            List<String> ses = mongoTemplate.query(Session.class).distinct("name").as(String.class).all();
            if(!ses.contains(sessionname)){
                createSession(sessionname, admin);
                admin.getSessions().add(sessionname);
                adminRepository.save(admin);
                model.addAttribute("sessionname", sessionname);
                String sessionurl = "http://syseraud.eu-central-1.elasticbeanstalk.com/Session/" + sessionname;
                BufferedImage img = createQRCodeForSession(sessionurl);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                String encoded;
                try{
                    ImageIO.write(img, "png", os);
                    encoded = Base64.getEncoder().encodeToString(os.toByteArray());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                model.addAttribute("encoded", encoded);
                model.addAttribute("sessionurl", sessionurl);
                return "session.html";
            }
            else {
                model.addAttribute("sessionfailed", true);;
            }
        }
        else {
            System.out.println("Admin nicht gefunden");

        }
        return "sessionstart.html";
    }

    private BufferedImage createQRCodeForSession(String sessionurl) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(sessionurl, BarcodeFormat.QR_CODE,500,500);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @PostMapping("/MainMenu/data")
    public String getData(@RequestParam(name="name") String name,Model model){
        Admin admin = adminRepository.findByName(name);
        Set<String> sessions = admin.getSessions();
        model.addAttribute("sessions",sessions);
        model.addAttribute("name",name);
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

    @GetMapping("/openImpressum")
    public String openImpressum(){
        return "impressum.html";
    }

    public void createSession(String sessionname, Admin admin) throws JsonProcessingException {
        ARSSession session = new ARSSession(admin, sessionname);
        arsSessionHandler.addSession(session);
        Session session1 = new Session();
        session1.setName(sessionname);
        session1.setAdmin(admin.getName());
        sessionRepository.save(session1);
    }
}
