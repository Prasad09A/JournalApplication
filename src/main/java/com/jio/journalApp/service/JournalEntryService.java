package com.jio.journalApp.service;

import com.jio.journalApp.entity.JournalEntry;
import com.jio.journalApp.entity.User;
import com.jio.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        /*user.getJournalEntries().removeIf(x->x.getId().equals(id));
        /* This line ensures that when a journal entry is deleted from the
         'journal_entries' collection, the corresponding reference is also removed
          from the user's 'journalEntries' list in the 'users' collection.
          If this line is commented out, deleting a journal entry from the
          'journal_entries' collection will not remove its reference from the
          'users' collection, resulting in a stale (or dangling) reference.
        */
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }


}
