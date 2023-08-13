package com.uni.ARS.session;

import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.QACardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class NotifyController {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    @Autowired
    private QACardRepository repo;

    public Map<String, List<SseEmitter>> sseEmitterMap = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(NotifyController.class);

    /**
     * Creates SseEmitter for user
     *
     * @param sessionname name of currently used session
     * @return SseEmiiter for user to recieve Server Sent Events
     */
    @CrossOrigin
    @GetMapping(value = "/notify/{sessionname}", consumes = MediaType.ALL_VALUE)
    public SseEmitter postEmitter(@PathVariable String sessionname){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e){
            e.printStackTrace();
        }
        if (sseEmitterMap.containsKey(sessionname)){
            List<SseEmitter> emitters = sseEmitterMap.get(sessionname);
            emitters.add(sseEmitter);
            sseEmitterMap.replace(sessionname, emitters);
            logger.trace("added emitter to existing list");
        }
        else {
            List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
            emitters.add(sseEmitter);
            sseEmitterMap.put(sessionname, emitters);
            logger.trace("added emitter to new list");
        }
        sseEmitter.onCompletion(()-> sseEmitterMap.get(sessionname).remove(sseEmitter));
        return sseEmitter;
    }

    /**
     * Sends notification to all stored SseEmitters according to notification type
     *
     * @param name name of notification type
     * @param sessionname name of currently used session
     * @param model model to add attributes to html response
     * @return html according to notification type
     */
    @PostMapping(value = "/notifychange/{sessionname}")
    public String sendNotification(@RequestParam String name,@PathVariable String sessionname, Model model){
        arsSessionHandler.getSession(sessionname).changeState();
        sendMessage(name, sessionname);
        model.addAttribute("name", name);
        if (name.equals("Answers")){
            return "adminsessionanswers.html";
        }
        else if(name.equals("Evaluation")){
            return "adminsessioneval.html";
        }
        else if(name.equals("Results")){
            List<QACard> cards = arsSessionHandler.getSession(sessionname).getAllCards();
            model.addAttribute("cards", cards);
            return "adminsessionresults.html";
        }
        else if(name.equals("StoreData")){
            ARSSession session = arsSessionHandler.getSession(sessionname);
            List<QACard> cards = session.getAllCards();
            for (QACard card:cards){
                repo.save(card);
            }
            model.addAttribute("name", session.admin.getName());
            arsSessionHandler.getArsSessions().remove(sessionname);
            return "mainmenu.html";
        }
        else {
            return "error.html";
        }

    }

    /**
     * Sends message to all connected SseEmitters
     *
     * @param name name of notification type
     * @param session name of currently used session
     */
    public void sendMessage(String name, String session){
        List<SseEmitter> sseEmitterList = sseEmitterMap.get(session);
        if (sseEmitterList!=null){
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
}
