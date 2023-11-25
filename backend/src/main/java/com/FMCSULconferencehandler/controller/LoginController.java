package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    private UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Participant reqUser) {
        Participant user=userService.getUserByEmail(reqUser.getEmail());
        Map<String, Object> object = new HashMap<>();

        if(user==null)
        {
            object.put("error", "user not found");
            return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
        }
        else if( reqUser.getPassword().equals(user.getPassword()))
        {
            object.put("user",user);
            return new ResponseEntity<>(object, HttpStatus.ACCEPTED);
        }
        else
        {
            object.put("error", "invalid password");
            return new ResponseEntity<>(object, HttpStatus.CONFLICT);
        }
    }


}
