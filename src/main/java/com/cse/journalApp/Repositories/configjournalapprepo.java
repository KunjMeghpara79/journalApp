package com.cse.journalApp.Repositories;

import com.cse.journalApp.entity.configjournalappentity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface configjournalapprepo extends MongoRepository<configjournalappentity, ObjectId> {

}
