package com.blabberchat.core.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("channels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Channel {

    @Id
    private ObjectId id;
    @Indexed
    private String name;

    private String picture;
    private String description;
    private LocalDateTime createdAt;

    private List<User> users;
    private List<User> owners;
}

