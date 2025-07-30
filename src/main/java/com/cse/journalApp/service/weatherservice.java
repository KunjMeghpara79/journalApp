package com.cse.journalApp.service;

import com.cse.journalApp.Appcache;
import com.cse.journalApp.api.response.weatherresponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class weatherservice {

    @Value("${weather.api.key}")
    private String apikey;

    @Autowired
    private Appcache cache;

    @Autowired
    private RestTemplate restTemplate;   // it is the class that processes the httprequest

    public weatherresponse getweather(String city){
        String final_api = cache.appCache.get("weather_api").replace("api_key",apikey).replace("city", city);
        ResponseEntity<weatherresponse> response = restTemplate.exchange(final_api, HttpMethod.GET,null, weatherresponse.class);   //The Process of converting json response into java entity(class) is called deserialization
        return response.getBody();
    }
}
