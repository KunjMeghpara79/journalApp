package com.cse.journalApp.Repositories;

import com.cse.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Journalentryrepo extends MongoRepository<JournalEntry, ObjectId> {
}
