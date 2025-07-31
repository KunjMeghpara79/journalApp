package com.cse.journalApp.repo;

import com.cse.journalApp.Repositories.userrepoimpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userrepoimpltest {
    @Autowired
    private userrepoimpl repoimpl;

    @Test
    public void sampletest(){

        repoimpl.getusersforSA();
    }
}
