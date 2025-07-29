package com.cse.journalApp.controller;

import com.cse.journalApp.entity.users;
import com.cse.journalApp.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class publiccontroller {

    @Autowired
    userservice service;
    @PostMapping
    public ResponseEntity<?> create(@RequestBody users user ){
        try {
            service.savenewuser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("already exist",HttpStatus.NOT_FOUND);
        }
    }
}
