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

    /**
     * Creates a new user with given name, stores to session and returns object
     *
     * @param username name of user
     * @param sessionname name of currently used session
     * @return created user
     */
    public User addUser(String username, String sessionname){
        ARSSession arsSession = arsSessionHandler.getSession(sessionname);
        User user = new User(nextId,username);
        arsSession.getUserList().put(nextId, user);
        nextId++;
        return user;
        //}
    }
}
