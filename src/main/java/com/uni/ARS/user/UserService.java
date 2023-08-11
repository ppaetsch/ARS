package com.uni.ARS.user;

import com.uni.ARS.session.ARSSession;
import com.uni.ARS.session.ARSSessionHandler;
import com.uni.ARS.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ARSSessionHandler arsSessionHandler;

    private Integer nextId = 0;

    public User addUser(String username, String sessionname){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        //if (!arsSession2.getUserList().containsKey("id")){
        User user = new User(nextId,username);
        arsSession.getUserList().put(nextId, user);
        nextId++;
        //arsSession2.users.add(username);
        return user;
        //}
    }
}
