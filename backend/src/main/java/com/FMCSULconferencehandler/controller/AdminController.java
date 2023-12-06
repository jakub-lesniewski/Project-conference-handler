package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.repositories.AdminRepository;
import com.FMCSULconferencehandler.model.Admin;
import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.FMCSULconferencehandler.controller.sha.Hashes.hashSHA512;

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

    // Adding a new admin account with generated UUID
    @PostMapping("/admin")
    public void addAdmin(@RequestBody Admin reqAdmin) {
        adminRepository.save(reqAdmin);
    }
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin reqAdmin)
    {                                                              //ISSUE: need to change the password inside the database to the SHA-512 hash of this
        Optional<Admin> admin = adminRepository.findByLoginAndPass(reqAdmin.getLogin(), hashSHA512(reqAdmin.getPass()));
        if(admin.isPresent())
        {
            return new ResponseEntity<>(admin.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("invalid data", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/addUser")
    public String  addUser( @RequestBody Participant... reqUsers)
    {
        for(Participant reqUser:reqUsers) {
            if (reqUser.getName() != null && reqUser.getSurname() != null && reqUser.getEmail() != null && reqUser.getAffiliation() != null) {

                reqUser.setPassword(passwordGenerate());
            }
            else
            {
                return "error";
            }
        }
        userService.add(reqUsers);
        return "Added "+ reqUsers.length +" participants";
    }

    @GetMapping("participant/{id}")
    public ResponseEntity<HashMap<String, Object>> getParticipant(@PathVariable("id") UUID id) {
        HashMap<String, Object> p;
        HashMap<String, Object> response = new HashMap();
        try {
            p = userService.getParticipant(id);

        } catch (RuntimeException e) {
            response.put("error", "no participant");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        response.put("user",p);
        return new ResponseEntity<>(response, HttpStatus.OK);

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



