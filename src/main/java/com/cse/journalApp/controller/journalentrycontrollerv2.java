package com.cse.journalApp.controller;

import com.cse.journalApp.entity.JournalEntry;
import com.cse.journalApp.entity.users;
import com.cse.journalApp.service.Journalentryservice;
import com.cse.journalApp.service.userservice;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalentrycontrollerv2 {

    @Autowired
    private Journalentryservice journalentryservice;

    @Autowired
    private userservice service;



    @GetMapping
    public ResponseEntity<?> getallentriesofuser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username  = auth.getName();
        users user = service.findbyusername(username);
        List<JournalEntry> res = user.getEntries();
        if (res != null && !res.isEmpty()) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createentry(@RequestBody JournalEntry myentry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username  = auth.getName();
        try {
            journalentryservice.saveentry(myentry, username);
            return new ResponseEntity<>(myentry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<?> getentrybyid(@PathVariable ObjectId myid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username  = auth.getName();
        users user = service.findbyusername(username);
        List<JournalEntry> collect = user.getEntries().stream().filter(x -> x.equals(myid)).collect(Collectors.toList());
        if(collect != null){
            Optional<JournalEntry> res = journalentryservice.findbyid(myid);
            if (res.isPresent()){
                return new ResponseEntity<>(res.get(),HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("not found",HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteentrybyid(@PathVariable ObjectId myid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username  = auth.getName();
        journalentryservice.deletebyid(myid, username);
        return new ResponseEntity<>(myid, HttpStatus.NO_CONTENT);
    }


    @PutMapping("id/{myid}")
    public ResponseEntity<?> updateentrybyid(@PathVariable ObjectId myid,@RequestBody JournalEntry newentry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username  = auth.getName();
        users user  = service.findbyusername(username);
        List<JournalEntry> list  = user.getEntries().stream().filter(x -> x.equals(myid)).collect(Collectors.toList());

        if(list != null){
            Optional<JournalEntry> res = journalentryservice.findbyid(myid);
            if (res.isPresent()){
                JournalEntry j = res.get();
                j.setTitle(newentry.getTitle() != null && !newentry.getTitle().isEmpty() ? newentry.getTitle() : j.getTitle());
                j.setContent(newentry.getContent() != null && !newentry.getContent().isEmpty() ? newentry.getContent() : j.getContent());
                journalentryservice.saveentry(j);
                return new ResponseEntity<>(j, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
