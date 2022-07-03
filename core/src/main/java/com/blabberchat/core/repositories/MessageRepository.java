package com.blabberchat.core.repositories;

import com.blabberchat.core.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
