package com.cse.journalApp.service;

import com.cse.journalApp.Repositories.userrepo;
import com.cse.journalApp.entity.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //The @Service annotation is a specialized version of @Component, used to mark a class that holds business logic.
public class userdetailserviceimpl implements UserDetailsService {  //UserDetailsService is built in service class which has load method.

    @Autowired
    userrepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // overriding  the load method
        users user = repo.findByusername(username);
        if(user != null){
            return User.builder()     // User is built in class for authentication which has variables like username , passwrod and roles
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("Username not found "+ username);
    }
}
