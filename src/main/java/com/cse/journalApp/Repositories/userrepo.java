package com.cse.journalApp.Repositories;

import com.cse.journalApp.entity.users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userrepo extends MongoRepository<users, ObjectId> {


    users findByusername(String username);
    void deleteByusername(String username);// it is a query method which is defined in Spring Data MongoDB
}
