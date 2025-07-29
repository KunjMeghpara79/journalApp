package com.cse.journalApp.service;

import com.cse.journalApp.Repositories.userrepo;
import com.cse.journalApp.entity.users;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest   //tells spring boot that this is the testing phase and consider this as test and run full application  context.
public class userservicetest {

    @Autowired
    private userrepo repo;

    @Autowired
    userservice service;
    @ParameterizedTest
    @ArgumentsSource(userargumentsprovider.class)
    public void testsavenewuser(users user){
        assertTrue(service.savenewuser(user));       // if it is true then test case will be passed.
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "3,3,6",
    })
    public void test(int a, int b, int c){
        assertEquals(c,a+b);
    }
}
