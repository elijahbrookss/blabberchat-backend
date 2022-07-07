package com.blabberchat.core.util;

import com.blabberchat.core.dtos.NewChannelDTO;
import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.dtos.RoleDTO;
import com.blabberchat.core.models.Channel;
import com.blabberchat.core.models.User;

import java.time.LocalDateTime;
import java.util.*;

public class MockUtil {

    public static NewUserDTO getNewUserDTO() {
        return NewUserDTO.builder()
                .username("test_user1")
                .profilePicture("https://profile-picture.com")
                .email("testuser1@gmail.com")
                .password("password")
                .roles(getRoleDTOs())
                .build();
    }

    public static User getUser() {
        return User.builder()
                .username("test_user2")
                .password("password")
                .profilePicture("https://profile-picture.com")
                .createdAt(LocalDateTime.now())
                .channels(new ArrayList<>())
                .roles(Set.of("USER_ROLE", "ADMIN_ROLE"))
                .build();
    }

    public static User getUser_inChannel(){
        return User.builder()
                .username("test_user3")
                .password("password")
                .profilePicture("https://profile-picture.png")
                .createdAt(LocalDateTime.now())
                .channels(new ArrayList<>())
                .roles(Set.of("USER_ROLE", "ADMIN_ROLE"))
                .build();
    }

    public static List<RoleDTO> getRoleDTOs() {
        return List.of(
                new RoleDTO("USER_ROLE"),
                new RoleDTO("ADMIN_ROLE")
        );
    }

    public static Channel getChannel() {
        return Channel.builder()
                .id(UUID.randomUUID().toString())
                .owner(User.builder().username("testuser").build())
                .description("This is a channel for testing purposes")
                .picture("https://channel-loop.png")
                .createdAt(LocalDateTime.now())
                .name("Test Channel 2")
                .users(new HashSet<>(Collections.singletonList(getUser_inChannel())))
                .build();
    }

    public static NewChannelDTO getChannelDTO() {
        return NewChannelDTO.builder()
                .name("Test Channel 1")
                .picture("https://channel-logo.png")
                .description("This is a channel for testing purposes")
                .ownerUsername(getUser().getUsername())
                .build();
    }

}
