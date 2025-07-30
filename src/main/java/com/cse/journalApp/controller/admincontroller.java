package com.cse.journalApp.controller;

import com.cse.journalApp.Appcache;
import com.cse.journalApp.entity.users;
import com.cse.journalApp.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class admincontroller {

    @Autowired
    userservice service;


    @Autowired
    private Appcache cache;
    @GetMapping("/all-users")
    public ResponseEntity<?> getallusersb(){
        List<users> list = service.getAll();
        if (list != null && !list.isEmpty()){
            return new ResponseEntity(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("create-admin")
    public ResponseEntity<?> createadminuser(@RequestBody users user){  // this will only work when there will be atleast one admin user because only admin can create new admin.
        service.saveadmin(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }


    @GetMapping("clear-cache")
    public  void clearcache(){
        cache.init();

    }
}
