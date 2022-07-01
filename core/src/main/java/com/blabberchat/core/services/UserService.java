package com.blabberchat.core.services;


import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(NewUserDTO newUserDTO) {
        User user = User.builder()
                .email(newUserDTO.getEmail())
                .username(newUserDTO.getUsername())
                .password(newUserDTO.getPassword())
                .profilePicture(newUserDTO.getProfilePicture())
                .createdAt(LocalDateTime.now())
                .channels(new ArrayList<>())
                .roles(newUserDTO.getRoles())
                .build();
        userRepository.save(user);

        return user;
    }

}







