package com.cse.journalApp.controller;

import com.cse.journalApp.entity.users;
import com.cse.journalApp.service.quoteservice;
import com.cse.journalApp.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publiccontroller {

    @Autowired
    userservice service;
    @Autowired
    private quoteservice qservice;
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
    @GetMapping("get-quote")
    public ResponseEntity<?> getquote(){
        try{
            return new ResponseEntity<>(qservice.getguote(),HttpStatus.FOUND);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
