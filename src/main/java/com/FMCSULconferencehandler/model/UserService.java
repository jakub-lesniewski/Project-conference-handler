package com.FMCSULconferencehandler.model;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class UserService {
    private LinkedList<User> users=new LinkedList<>();
    public void add(User user)
    {
        users.add(user);
    }
    public User getUserByEmail(String email)
    {
        for(User user:users)
        {
            if(user.getEmail_login().equals(email))
            {
                return user;
            }
        }
        return null;
    }
}
