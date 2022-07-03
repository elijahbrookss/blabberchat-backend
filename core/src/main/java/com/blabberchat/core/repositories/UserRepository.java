package com.blabberchat.core.repositories;

import com.blabberchat.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
        Optional<User> findByUsername(String username);
}

