package com.cse.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class redisservice {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;  // RedisTemplate configured for String keys & values

    @Autowired
    private ObjectMapper objectMapper;  // For JSON serialization/deserialization

    // Generic method to retrieve object from Redis by key and convert it to the given class type
    public <T> T get(String key, Class<T> clazz) {
        try {
            String json = redisTemplate.opsForValue().get(key);  // Get JSON string from Redis
            if (json == null) {
                log.info("Redis MISS for key: {}", key);
                return null;
            }
            log.info("Redis HIT for key: {}", key);
            return objectMapper.readValue(json, clazz);  // Deserialize JSON to Java object
        } catch (Exception e) {
            log.error("Redis GET error for key {}: {}", key, e.getMessage());
            return null;
        }
    }

    // Store any object as JSON in Redis with TTL (in seconds)
    public void set(String key, Object object, long ttlSeconds) {
        try {
            String json = objectMapper.writeValueAsString(object);  // Convert object to JSON string
            redisTemplate.opsForValue().set(key, json, ttlSeconds, TimeUnit.SECONDS);  // Store in Redis
            log.info("Redis SET key: {} with TTL: {} seconds", key, ttlSeconds);
        } catch (Exception e) {
            log.error("Redis SET error for key {}: {}", key, e.getMessage());
        }
    }
}
