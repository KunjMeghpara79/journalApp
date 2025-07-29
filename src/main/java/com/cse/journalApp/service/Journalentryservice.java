package com.cse.journalApp.service;

import com.cse.journalApp.Repositories.Journalentryrepo;
import com.cse.journalApp.entity.JournalEntry;
import com.cse.journalApp.entity.users;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component  //The @Component annotation marks a Java class as a Spring-managed component (bean).
@Slf4j
public class Journalentryservice {
    @Autowired
    public Journalentryrepo journalentryrepo;

    @Autowired
    private userservice service;



    @Transactional  //thiṡ annotaion means the method will be executed either 100% or 0% means rollback
    public void saveentry(JournalEntry journalEntry, String username){

        try {
            users user = service.findbyusername(username); // find the user to save the entry in their acc
            journalEntry.setDate(LocalDate.now());
            JournalEntry saved =  journalentryrepo.save(journalEntry);
            user.getEntries().add(saved); // add the new entry in the existing entries of user.
            service.saveuser(user); // after adding you also need to save that user in the db.  and this time new user will not be created and the password will  be encoded and wont be ancoded again
        }
        catch (Exception e){
            log.error("error ",e);
            throw new RuntimeException(e);
        }

    }

    public void saveentry(JournalEntry j){
        journalentryrepo.save(j); // in user collection there is only the id of the entry so when  update method is called we only need to update that entry because the id will not be changed.
    }

    public List<JournalEntry> printentry(){
        return journalentryrepo.findAll();
    }

    public Optional<JournalEntry> findbyid(ObjectId id){
        return journalentryrepo.findById(id);
    }

    @Transactional
    public void deletebyid(ObjectId id, String username){
        users user = service.findbyusername(username);
        boolean removed = user.getEntries().removeIf(x -> x.getId().equals(id));

        if(removed){
            service.saveuser(user);
        journalentryrepo.deleteById(id);}
    }

    public List<JournalEntry> findByusername(String username){
        users user = service.findbyusername(username);
        return user.getEntries();
    }
}
