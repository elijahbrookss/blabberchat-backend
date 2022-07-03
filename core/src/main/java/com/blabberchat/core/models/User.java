package com.blabberchat.core.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Document("users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;

    @Indexed
    private String email;

    @Indexed
    private String username;

    @JsonIgnore
    private String password;

    private String profilePicture;
    private LocalDateTime createdAt;
    private Set<String> roles;

    @DBRef
    @JsonIgnoreProperties({ "users", "owner" })
    private List<Channel> channels;
}
