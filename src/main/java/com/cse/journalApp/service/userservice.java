package com.cse.journalApp.service;

import com.cse.journalApp.Repositories.userrepo;
import com.cse.journalApp.entity.users;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class userservice {
    @Autowired
    public userrepo userrep;

//    private static final Logger logger = LoggerFactory.getLogger(userservice.class);
//    // it loads the logger from  LoggerFactory utility class from slf4j package
//    // and slf4j is the abstraction of logback (one of the type of logging frameworks
//



    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();



    public boolean savenewuser(users user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userrep.save(user);
            return true;
        }
        catch (Exception e){
            log.error("error occured for user {} : ",user.getUsername());
            return false;
        }
    }

    public void saveuser(users user){
        userrep.save(user);
    }

    public List<users> printentry() {
        return userrep.findAll();
    }

    public Optional<users> findbyid(ObjectId id) {
        return userrep.findById(id);
    }

    public void deletebyid(ObjectId id) {
        userrep.deleteById(id);
    }

    public users findbyusername(String username) {
        return userrep.findByusername(username);
    }
    public List<users> getAll() {
        return userrep.findAll();
    }


    public void saveadmin(users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER" , "ADMIN"));
        userrep.save(user);

    }
}
