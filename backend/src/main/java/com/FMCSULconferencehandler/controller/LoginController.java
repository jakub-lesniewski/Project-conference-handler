package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private UserService userService;
    public LoginController(UserService userService) {
        this.userService = userService;
        //userService.add(new Participant("XD","XD","XD","XD","MOJEHASLO",0));
    }

    //@Autowired


    @PostMapping("/login")
    public Participant login(Participant reqUser) {
        Participant user=userService.getUserByEmail(reqUser.getEmail_login());
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
    public boolean  addUser(Participant reqUser)
    {
        if(reqUser.getName()!=null && reqUser.getSurname()!=null && reqUser.getEmail_login()!=null && reqUser.getAffilation()!=null) {
            userService.add(reqUser);
            return  true;
        }
        return false;
    }



}
