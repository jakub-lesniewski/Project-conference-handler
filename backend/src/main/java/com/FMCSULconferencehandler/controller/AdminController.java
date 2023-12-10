package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.repositories.AdminRepository;
import com.FMCSULconferencehandler.model.Admin;
import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.service.AdminService;
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
    private AdminService adminService;

    public  AdminController(UserService userService,AdminService adminService)
    {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/participants")
    public List<Participant> getParticipants()
    {
        return userService.getParticipants();
    }

    // Adding a new admin account with generated UUID
    @PostMapping("/admin")
    public ResponseEntity<?> addAdmin(@RequestBody Admin reqAdmin) {
        if(adminService.saveAdmin(reqAdmin))
            return new ResponseEntity<>(reqAdmin, HttpStatus.CREATED);
        else
            return new ResponseEntity<>("invalid username", HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin reqAdmin)
    {                                                              //ISSUE: need to change the password inside the database to the SHA-512 hash of this
        Optional<Admin> admin = adminService.findByLogin(reqAdmin.getLogin());
        Map<String, Object> object = new HashMap<>();

        if(!admin.isPresent()){
            object.put("error", "admin not found");
            return new ResponseEntity<>(object, HttpStatus.UNAUTHORIZED);
        }
        else if(hashSHA512(reqAdmin.getPass()).equals(admin.get().getPass())){
            object.put("admin", admin);
            return new ResponseEntity<>(object, HttpStatus.OK);
        }
        else {
            object.put("error", "invalid password");
            return new ResponseEntity<>(object, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser( @RequestBody Participant... reqUsers)
    {
        for(Participant reqUser:reqUsers) {
            if (adminService.checkName(reqUser.getName()) && adminService.checkName(reqUser.getSurname())
                    && adminService.checkMail(reqUser.getEmail())) {
                reqUser.setPassword(adminService.passwordGenerate());
            }
            else
            {
                return new ResponseEntity<>("error", HttpStatus.CONFLICT);
            }
        }
        userService.add(reqUsers);
        return new ResponseEntity<>("Added "+ reqUsers.length +" participants", HttpStatus.OK);
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
}



