package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.AdminRepository;
import com.FMCSULconferencehandler.model.Admin;
import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private AdminRepository adminRepository;
    public  AdminController(UserService userService,AdminRepository adminRepository)
    {
        this.userService=userService;
        this.adminRepository=adminRepository;
    }

    @PostMapping("/login")
    public Admin  adminLogin(Admin reqAdmin)
    {

        Admin admin=adminRepository.findAll().get(0);
        if(admin.getLogin().equals(reqAdmin.getLogin()) && admin.getPass().equals(reqAdmin.getPass()))
        {
            return admin;
        }
        return null;
    }

    @PostMapping("/addUser")
    public boolean  addUser(Participant reqUser)
    {
        if(reqUser.getName()!=null && reqUser.getSurname()!=null && reqUser.getEmail_login()!=null && reqUser.getAffilation()!=null) {
            reqUser.setPassword(passwordGenerate());
            userService.add(reqUser);
            return  true;
        }
        return false;
    }
    private String passwordGenerate()
    {
        String password="";
        Random random=new Random();
        for(int i=0;i<8;i++)
        {
            password=password+((random.nextInt(10)));
        }
        return password;
    }

}
