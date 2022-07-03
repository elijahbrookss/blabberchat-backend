package com.blabberchat.core.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Document("channels")
@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class Channel {

    @Id
    private String id;
    @Indexed
    private String name;

    private String picture;
    private String description;
    private LocalDateTime createdAt;

    @JsonIgnoreProperties("channels")
    private Set<User> users;

    @JsonIgnoreProperties("channels")
    private User owner;
}

