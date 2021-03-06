package com.blabberchat.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("reactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {

    @Id
    private String id;

    private String icon;

    private User user;
    private Message message;
}
