package com.blabberchat.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("roles")
@Builder @Data
@AllArgsConstructor @NoArgsConstructor
public class Role {
    @Id
    private String id;

    @Indexed
    private String name;
}
