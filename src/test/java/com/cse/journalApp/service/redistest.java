package com.cse.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redistest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Disabled
    @Test
    public void redistesting(){
        redisTemplate.opsForValue().set("email","kunjmeghapara777@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a =1;
    }
}
