package com.cse.journalApp.controller;

import com.cse.journalApp.Repositories.userrepo;
import com.cse.journalApp.entity.users;
import com.cse.journalApp.service.userservice;
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
    userrepo repo;



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
        repo.deleteByusername(auth.getName());
        return new ResponseEntity<>("Deleted",HttpStatus.NO_CONTENT);

    }

}
