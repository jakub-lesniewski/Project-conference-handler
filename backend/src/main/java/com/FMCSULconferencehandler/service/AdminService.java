package com.FMCSULconferencehandler.service;

import com.FMCSULconferencehandler.model.Admin;
import com.FMCSULconferencehandler.model.Session;
import com.FMCSULconferencehandler.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class AdminService {
    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public boolean saveAdmin(Admin reqAdmin) {
        Optional<Admin> admin = adminRepository.findByLogin(reqAdmin.getLogin());

        if(!admin.isPresent()){
            adminRepository.save(reqAdmin);
            return true;
        }
        return false;
    }

    public Optional<Admin> findByLogin(String login){
        return adminRepository.findByLogin(login);
    }

    public String passwordGenerate()
    {
        String password="";
        Random random=new Random();
        for(int i=0;i<8;i++)
        {
            password=password+((random.nextInt(10)));
        }
        return password;
    }
    public boolean checkName(String name){
        if(name == null)
            return false;
        if(name.charAt(0) < 65 || name.charAt(0) > 90)
            return false;
        for(int i = 1; i < name.length(); i++)
            if(name.charAt(i) < 97 || name.charAt(i) > 122)
                return false;
        return true;
    }

    public boolean checkMail(String mail){
        return Pattern.compile("^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")
                .matcher(mail)
                .matches();
    }
}
