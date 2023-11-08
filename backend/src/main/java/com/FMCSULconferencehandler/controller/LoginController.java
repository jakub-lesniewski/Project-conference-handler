package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping("/login")
    public Object login(@RequestBody Participant reqUser) {
        Participant user=userService.getUserByEmail(reqUser.getEmail_login());
        if(user==null)
        {
            return "USER NOT FOUND";
        }
        else if( reqUser.getPassword().equals(user.getPassword()))
        {
            return user;
        }
        else
        {
            return "INVALID PASSWORD";
        }
    }

}
