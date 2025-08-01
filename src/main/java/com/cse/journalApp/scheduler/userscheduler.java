package com.cse.journalApp.scheduler;

import com.cse.journalApp.Appcache;
import com.cse.journalApp.Repositories.userrepoimpl;
import com.cse.journalApp.entity.JournalEntry;
import com.cse.journalApp.entity.users;
import com.cse.journalApp.service.emailservice;
import com.cse.journalApp.service.sentimentanalysisservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class userscheduler {


    @Autowired
    private userrepoimpl repoimpl;

    @Autowired
    private sentimentanalysisservice sentimentservice;

    @Autowired
    private Appcache appcache;

    @Autowired
    private emailservice service;

    @Scheduled(cron = "0 0 10 ? * FRI")
    public void fetchusersandsendmail(){
        List<users> list = repoimpl.getusersforSA();
        for(users user : list){
            List<JournalEntry> entries = user.getEntries();
            List<String> filteredentries  = entries.stream().filter(x -> x.getDate().isAfter(ChronoLocalDate.from(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))).map(x->x.getContent()).collect(Collectors.toList());
            String combined = String.join(" ",filteredentries);
            String sentiment = sentimentservice.getsentiment(combined);
           service.sendmail(user.getEmail(),"Sentiment for last week",sentiment);
        }
    }
    @Scheduled(cron = "0 0/5 * ? * *")
    public void clearappcache(){
        appcache.init();
    }
}
