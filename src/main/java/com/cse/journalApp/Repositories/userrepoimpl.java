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
    private MongoTemplate mongoTemplate;

    public List<users> getusersforSA() {
        Query query = new Query();

        // Email must be valid
        Criteria emailCriteria = new Criteria().andOperator(
                Criteria.where("email").ne(null),
                Criteria.where("email").ne(""),
                Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        );

        // Role must be exactly ["USER"]
        Criteria exactRoleCriteria = Criteria.where("roles").is(List.of("USER"));

        // Sentiment analysis must be enabled
        Criteria sentimentCriteria = Criteria.where("sentimentanalysis").is(true);

        // Add to query
        query.addCriteria(emailCriteria);
        query.addCriteria(exactRoleCriteria);
        query.addCriteria(sentimentCriteria);

        return mongoTemplate.find(query, users.class);
    }
}
