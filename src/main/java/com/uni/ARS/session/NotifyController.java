package com.uni.ARS.session;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class NotifyController {

    public List<SseEmitter> sseEmitterList = new CopyOnWriteArrayList<>();


    @CrossOrigin
    @GetMapping(value = "/notify", consumes = MediaType.ALL_VALUE)
    public SseEmitter getEmitter(){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e){
            e.printStackTrace();
        }
        sseEmitter.onCompletion(()-> sseEmitterList.remove(sseEmitter));
        sseEmitterList.add(sseEmitter);
        return sseEmitter;
    }

    @PostMapping(value = "/notifychange")
    public String sendNotification(@RequestParam String name, Model model){
        sendMessage(name);
        model.addAttribute("name", name);
        return "adminsession.html";



    }


    public void sendMessage(String name){
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
