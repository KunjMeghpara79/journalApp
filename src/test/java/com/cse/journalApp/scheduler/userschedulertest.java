package com.cse.journalApp.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userschedulertest {


    @Autowired
    private userscheduler scheduler;

    @Test
    public void emailtest(){
        scheduler.fetchusersandsendmail();
    }
}
