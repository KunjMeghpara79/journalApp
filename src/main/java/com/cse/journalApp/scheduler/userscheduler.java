package com.cse.journalApp.scheduler;

import com.cse.journalApp.Appcache;
import com.cse.journalApp.Repositories.userrepoimpl;
import com.cse.journalApp.entity.JournalEntry;
import com.cse.journalApp.entity.users;
import com.cse.journalApp.enums.sentiment;
import com.cse.journalApp.model.sentimentdata;
import com.cse.journalApp.service.emailservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class userscheduler {


    @Autowired
    private userrepoimpl repoimpl;


    @Autowired
    private Appcache appcache;

    @Autowired
    private emailservice service;

    @Autowired
    private KafkaTemplate<String, sentimentdata> kafkaTemplate;

    @Scheduled(cron = "0 0 10 ? * FRI")
    public void fetchusersandsendmail(){
        List<users> list = repoimpl.getusersforSA();
        for(users user : list){
            List<JournalEntry> entries = user.getEntries();
            List<sentiment> sentiments  = entries.stream().filter(x -> x.getDate().isAfter(ChronoLocalDate.from(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<sentiment, Integer> sentimentCounts = new HashMap<>();
            for (sentiment s : sentiments) {
                if (s != null) {
                    sentimentCounts.put(s, sentimentCounts.getOrDefault(s, 0) + 1);
                }
            }

            sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                sentimentdata sentimentData = sentimentdata.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                try{
                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                }catch (Exception e){
                    service.sendmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                }
            }
        }
    }
    @Scheduled(cron = "0 0/5 * ? * *")
    public void clearappcache(){
        appcache.init();
    }
}
