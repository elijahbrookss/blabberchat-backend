package com.blabberchat.core.repositories;

import com.blabberchat.core.models.Reaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReactionRepository extends MongoRepository<Reaction, String> {
}
