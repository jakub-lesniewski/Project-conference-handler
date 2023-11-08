package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.AdminRepository;
import com.FMCSULconferencehandler.model.Admin;
import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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


    @GetMapping("/participants")
    public List<Participant> getParticipants()
    {
        return userService.getParticipants();
    }


    @PostMapping("/login")
    public Object  adminLogin(@RequestBody Admin reqAdmin)
    {

        Admin admin=adminRepository.findAll().get(0);
        if(admin.getLogin().equals(reqAdmin.getLogin()) && admin.getPass().equals(reqAdmin.getPass()))
        {
            return admin;
        }
        return "INVALID DATA";
    }

    @PostMapping("/addUser")
    public String  addUser( @RequestBody Participant... reqUsers)
    {
        int x=0;
        for(Participant reqUser:reqUsers) {
            if (reqUser.getName() != null && reqUser.getSurname() != null && reqUser.getEmail_login() != null && reqUser.getAffilation() != null) {
                reqUser.setPassword(passwordGenerate());
                x++;
            }
            else
            {
                return "Error";
            }
        }
        userService.add(reqUsers);
        return "Added "+x+" participants";
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
