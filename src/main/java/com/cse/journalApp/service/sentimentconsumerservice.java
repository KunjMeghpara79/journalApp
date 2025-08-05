package com.cse.journalApp.service;

import com.cse.journalApp.model.sentimentdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class sentimentconsumerservice {

    @Autowired
    private emailservice service;

    @KafkaListener(topics = "weekly-sentiments",groupId = "weekly-sentiment-group")
    public void consume(sentimentdata data){
         sendmail(data);
    }

    public void sendmail(sentimentdata data){
        service.sendmail(data.getEmail(),"Sentiment for previous week",data.getSentiment());
    }
}
