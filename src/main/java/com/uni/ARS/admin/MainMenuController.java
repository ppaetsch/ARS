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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
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

    private Json json = new Json();

    @GetMapping("/MainMenu")
    public String getMainMenu(){
        System.out.println("Start Main Menu");
        return "";
    }

    @PostMapping("/MainMenu/session")
    public String getSession(@RequestParam(name="sessionname") String sessionname, @RequestParam(name="name") String name, Model model) throws IOException, WriterException {
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
