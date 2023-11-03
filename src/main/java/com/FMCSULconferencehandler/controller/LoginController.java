package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.User;
import com.FMCSULconferencehandler.model.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    public LoginController(UserService userService) {
        this.userService = userService;
        userService.add(new User("XD","XD","XD","XD","MOJEHASLO",0));
    }

    //@Autowired
    private UserService userService;

    @PostMapping ("/login")
    public User login(User reqUser) {
        User user=userService.getUserByEmail(reqUser.getEmail_login());
        if(user!=null && reqUser.getPassword().equals(user.getPassword()))
        {
            return user;
        }
        else
        {
            return null;
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<String>  addUser(@RequestBody User reqUser)
    {
        userService.add(reqUser);
        return ResponseEntity.ok("user added");
    }



}
