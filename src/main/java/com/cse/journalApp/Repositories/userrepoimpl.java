package com.cse.journalApp.Repositories;

import com.cse.journalApp.entity.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class userrepoimpl {


    @Autowired
    private MongoTemplate mongoTemplate;  //Spring-Data-MongoDB gives functionalitites to interact with mongodb using this class


    public List<users> getusersforSA() {
        Query query = new Query();

        Criteria emailCriteria = new Criteria().andOperator(
                Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"),
                Criteria.where("email").ne(null),
                Criteria.where("email").ne("")
        );

        query.addCriteria(emailCriteria);
        query.addCriteria(Criteria.where("sentimentanalysis").is(true));

        List<users> list = mongoTemplate.find(query, users.class);
        System.out.println(list);
        return list;
    }

}
