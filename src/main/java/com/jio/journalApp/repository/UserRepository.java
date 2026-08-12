package com.jio.journalApp.repository;

import com.jio.journalApp.entity.JournalEntry;
import com.jio.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
