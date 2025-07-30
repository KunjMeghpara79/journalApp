package com.cse.journalApp.controller;

import com.cse.journalApp.Repositories.Journalentryrepo;
import com.cse.journalApp.Repositories.userrepo;
import com.cse.journalApp.api.response.weatherresponse;
import com.cse.journalApp.entity.users;
import com.cse.journalApp.service.userservice;
import com.cse.journalApp.service.weatherservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class usercontroller {

    @Autowired
    private userservice service;

    @Autowired
    private Journalentryrepo jrepo;

    @Autowired
    userrepo repo;

    @Autowired
    private weatherservice weatherservice;


    @PutMapping
    public ResponseEntity<?> editname(@RequestBody users user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();   // from this authentication getName method will extract the username.
        users userindb = service.findbyusername(username);

        userindb.setUsername(user.getUsername());
        userindb.setPassword(user.getPassword());

        service.savenewuser(userindb);
        return new ResponseEntity<>(userindb, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteuser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        users user = service.findbyusername(auth.getName());
        // Delete all journal entries referenced in the user
        if (user.getEntries() != null) {
            user.getEntries().forEach(entry -> {
                jrepo.deleteById(entry.getId());
            });
        }


        repo.deleteByusername(auth.getName());
        return new ResponseEntity<>("Deleted",HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        weatherresponse response = weatherservice.getweather("Mumbai");
        String greeting = "";
        if(response != null) {
            greeting = "The Weather feels like " + response.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>("hii "+ auth.getName() + greeting,HttpStatus.OK);
    }

}
