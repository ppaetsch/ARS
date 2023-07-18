package com.uni.ARS.session;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class NotifyController {

    //public List<SseEmitter> sseEmitterList = new CopyOnWriteArrayList<>();
    public Map<String, List<SseEmitter>> sseEmitterMap = new HashMap<>();


    @CrossOrigin
    @GetMapping(value = "/notify/{sessionname}", consumes = MediaType.ALL_VALUE)
    public SseEmitter postEmitter(@PathVariable String sessionname){
        System.out.println("Emitter Anfrage wird erstellt mit: " + sessionname);
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e){
            e.printStackTrace();
        }
        //sseEmitter.onCompletion(()-> sseEmitterList.remove(sseEmitter));
        if (sseEmitterMap.containsKey(sessionname)){
            List<SseEmitter> emitters = sseEmitterMap.get(sessionname);
            emitters.add(sseEmitter);
            sseEmitterMap.replace(sessionname, emitters);
            System.out.println("Erfolgreich hinzugefügt in alte Liste");
        }
        else {
            List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
            emitters.add(sseEmitter);
            sseEmitterMap.put(sessionname, emitters);
            System.out.println("Erfolgreich hinzugefügt in neue Liste");
        }
        //sseEmitterList.add(sseEmitter);
        return sseEmitter;
    }


    @CrossOrigin
    @GetMapping(value = "/notify", consumes = MediaType.ALL_VALUE)
    public SseEmitter getEmitter(){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e){
            e.printStackTrace();
        }
        //sseEmitter.onCompletion(()-> sseEmitterList.remove(sseEmitter));
        //sseEmitterList.add(sseEmitter);
        return sseEmitter;
    }

    @PostMapping(value = "/notifychange/{sessionname}")
    public String sendNotification(@RequestParam String name,@PathVariable String sessionname, Model model){
        sendMessage(name, sessionname);
        model.addAttribute("name", name);
        return "adminsession.html";
    }


    public void sendMessage(String name, String session){
        List<SseEmitter> sseEmitterList = sseEmitterMap.get(session);
        for (SseEmitter emitter:sseEmitterList){
            try {
                emitter.send(SseEmitter.event().name("name").data(name));
            } catch (IOException e){
                sseEmitterList.remove(emitter);
                e.printStackTrace();
            }
        }
    }
}
