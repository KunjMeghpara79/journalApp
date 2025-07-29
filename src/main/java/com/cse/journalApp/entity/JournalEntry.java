package com.cse.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "journal_entries")
@Data    // it a lombok annotation from which we do not have to create getters and setters.
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDate date;


}
