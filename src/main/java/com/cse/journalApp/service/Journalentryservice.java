package com.cse.journalApp.service;

import com.cse.journalApp.Repositories.Journalentryrepo;
import com.cse.journalApp.entity.JournalEntry;
import com.cse.journalApp.entity.users;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service  // The @Component annotation marks this class as a Spring-managed component (bean).
@Slf4j    // We can use service annotation instead of Component annotation both adds beans but service annotaion tells reader that this class contains buisness logic
public class Journalentryservice {

    @Autowired
    public Journalentryrepo journalentryrepo;

    @Autowired
    private userservice service;

    @Transactional  // Ensures the method is atomic: either fully succeeds or rolls back.
    public void saveentry(JournalEntry journalEntry, String username) {
        try {
            users user = service.findbyusername(username);
            journalEntry.setDate(LocalDate.now());

            JournalEntry saved = journalentryrepo.save(journalEntry);

            if (user.getEntries() == null) {
                user.setEntries(new ArrayList<>());
                log.warn("Entries list was null for user '{}'. Initialized to empty list.", username);
            }

            user.getEntries().add(saved);
            service.saveuser(user);
        } catch (Exception e) {
            log.error("Error while saving journal entry for user '{}': {}", username, e.toString(), e);
            throw new RuntimeException(e);
        }
    }

    public void saveentry(JournalEntry j) {
        journalentryrepo.save(j);
    }

    public List<JournalEntry> printentry() {
        return journalentryrepo.findAll();
    }

    public Optional<JournalEntry> findbyid(ObjectId id) {
        return journalentryrepo.findById(id);
    }

    @Transactional
    public void deletebyid(ObjectId id, String username) {
        users user = service.findbyusername(username);

        if (user.getEntries() == null) {
            log.warn("Entries list was null for user '{}'. Nothing to delete.", username);
            return;
        }

        boolean removed = user.getEntries().removeIf(entry -> entry.getId().equals(id));

        if (removed) {
            service.saveuser(user);
            journalentryrepo.deleteById(id);
        } else {
            log.warn("No journal entry with id '{}' found for user '{}'.", id.toHexString(), username);
        }
    }

    public List<JournalEntry> findByusername(String username) {
        users user = service.findbyusername(username);

        if (user.getEntries() == null) {
            log.warn("Entries list is null for user '{}'. Returning empty list.", username);
            return new ArrayList<>();
        }

        return user.getEntries();
    }
}
