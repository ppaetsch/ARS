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

    int qrcodecounter = 0;

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

    private Json json = new Json();

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
            System.out.println(ses.size() + " länge der Liste");
            if(!ses.contains(sessionname)){
                createSession(sessionname, admin);
                admin.getSessions().add(sessionname);
                adminRepository.save(admin);
                model.addAttribute("sessionname", sessionname);
                BufferedImage img = createQRCodeForSession(sessionname, "./src/main/resources/static/qrcodes/");
                String qrcodename = "QRCode" + Integer.toString(qrcodecounter-1) + ".png";
                System.out.println(qrcodename);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                String encoded;
                try{
                    ImageIO.write(img, "png", os);
                    encoded = Base64.getEncoder().encodeToString(os.toByteArray());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                model.addAttribute("encoded", encoded);
                model.addAttribute("qrcodename", qrcodename);
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

    private BufferedImage createQRCodeForSession(String sessionname, String path) throws WriterException, IOException {
        String barcode = "http://192.168.178.46:8080/Session/" + sessionname;
        path = path + "QRCode" + Integer.toString(qrcodecounter) + ".png";
        System.out.println(path);
        qrcodecounter++;
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(barcode, BarcodeFormat.QR_CODE,500,500);
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

    public void createSession(String sessionname, Admin admin) throws JsonProcessingException {
        ARSSession session = new ARSSession(admin, sessionname);
        arsSessionHandler.addSession(session);
        Session session1 = new Session();
        session1.setName(sessionname);
        session1.setAdmin(admin.getName());
        sessionRepository.save(session1);
    }
}
