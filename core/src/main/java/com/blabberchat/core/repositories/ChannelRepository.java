package com.blabberchat.core.repositories;

import com.blabberchat.core.models.Channel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChannelRepository extends MongoRepository<Channel, ObjectId> {
}
