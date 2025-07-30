package com.cse.journalApp;

import com.cse.journalApp.Repositories.configjournalapprepo;
import com.cse.journalApp.entity.configjournalappentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Appcache {



    public Map<String,String> appCache;

    @Autowired
    private configjournalapprepo configrepo;

    @PostConstruct    // this annotation is used for doing task whenever the bean is loaded
    public void init(){
        appCache = new HashMap<>();
        List<configjournalappentity> all =  configrepo.findAll();
        for(configjournalappentity entity : all){
            appCache.put(entity.getKey(),entity.getValue());
        }
    }
}
