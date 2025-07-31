package com.cse.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class emailservicetest {

    @Autowired
    private emailservice service;

    @Test
    public void sendemailtest(){
        service.sendmail("yashkhanpara11@gmail.com","this is subject","This is body");
    }

}
