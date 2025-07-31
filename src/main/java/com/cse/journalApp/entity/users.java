package com.cse.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data // this is a lombok annotation which is used to get getters and setters no  need to type in the class
@Builder   // this is also a lombom annotaion from which we can create an object of the class with .build method
@NoArgsConstructor
@AllArgsConstructor
public class users {
    @Id // object id of mongo document
    private ObjectId id;
    @Indexed(unique = true) // keeps username unique
    @NonNull//ensures that the feild is not null
    private String username;
    @NonNull
    private String password;

    private String email;
    private boolean sentimentanalysis;


    @DBRef//reference for the user for the journal entries that user have created
    @Builder.Default
    private List<JournalEntry> entries = new ArrayList<>();

    private List<String> roles;

}
