package com.cse.journalApp.service;

import com.cse.journalApp.Appcache;
import com.cse.journalApp.api.response.quoteresponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class quoteservice {

    @Autowired
    private  Appcache cache;


    @Value("${quote.api.key}")
    private  String apikey;


    public quoteresponse getguote() {
         String url = cache.appCache.get("quote_api");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apikey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        // Use array instead of List + ParameterizedTypeReference
        ResponseEntity<quoteresponse[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                quoteresponse[].class
        );

        quoteresponse[] quotes = response.getBody();
        if (quotes != null && quotes.length > 0) {
            return quotes[0]; // Return the first quote
        }

        return null;
    }
}
