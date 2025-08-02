package com.cse.journalApp.service;

import com.cse.journalApp.Appcache;
import com.cse.journalApp.api.response.weatherresponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class weatherservice {

    @Value("${weather.api.key}")
    private String apikey;  // Inject weather API key from properties

    @Autowired
    private Appcache cache;  // Class holding API templates (weather API URL)

    @Autowired
    private redisservice service;  // Redis cache handler

    @Autowired
    private RestTemplate restTemplate;  // For making HTTP calls

    // Method to get weather info for a given city (from Redis if cached, otherwise via API)
    public weatherresponse getweather(String city) throws JsonProcessingException {
        String redisKey = "Weather_of_" + city;

        // First, try fetching from Redis
        weatherresponse cached = service.get(redisKey, weatherresponse.class);
        if (cached != null) {
            return cached;  // Return cached response
        }

        // Build final API URL by replacing placeholders
        String final_api = cache.appCache.get("weather_api")
                .replace("api_key", apikey)
                .replace("city", city);

        // Call weather API
        ResponseEntity<weatherresponse> response = restTemplate.exchange(
                final_api, HttpMethod.GET, null, weatherresponse.class
        );

        // If API gave valid response, store it in Redis
        if (response.getBody() != null) {
            service.set(redisKey, response.getBody(), 300L);  // Cache for 5 minutes (300 seconds)
        }

        return response.getBody();  // Return the fresh API response
    }
}
