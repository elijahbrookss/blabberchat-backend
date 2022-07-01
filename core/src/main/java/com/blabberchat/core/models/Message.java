package com.blabberchat.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private String id;

    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User user;
    private Channel channel;
    private List<Reaction> reactions;
}


