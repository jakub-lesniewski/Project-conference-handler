package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.User;
import com.FMCSULconferencehandler.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping ("/login")
    public ResponseEntity<String> login(@RequestBody User reqUser)
    {
        User user=userService.getUserByEmail(reqUser.getEmail_login());
        if(user!=null && reqUser.getPassword().equals(user.getPassword()))
        {
            return ResponseEntity.ok("Login succesfull");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid data");
        }
    }


}
