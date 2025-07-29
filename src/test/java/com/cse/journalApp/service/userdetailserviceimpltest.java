package com.cse.journalApp.service;

import com.cse.journalApp.Repositories.userrepo;
import com.cse.journalApp.entity.users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
@ActiveProfiles("dev")
public class userdetailserviceimpltest {

    @InjectMocks // it injects all required mocks no need of real service and create the instance automatically.
    private userdetailserviceimpl detailserviceimpl;

    @Mock
    private userrepo repo;   // when the test will run programm will not user repo instead it will test only what we have provided. i wont fetch from database.
    //and this is called mocking

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);    // before each test it will initialize all mocks.
    }

    @Test
    void loaduserbyusernametest(){
        when(repo.findByusername(ArgumentMatchers.anyString())).thenReturn(users.builder().username("ram").password("hvjhbjsbz").roles(new ArrayList<>()).build());
        UserDetails user =  detailserviceimpl.loadUserByUsername("ram");
        Assertions.assertNotNull(user);

     }
}
