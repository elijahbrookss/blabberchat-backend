package com.blabberchat.core.services;

import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MainService {

    private final UserRepository userRepository;
    public User createNewUser(NewUserDTO newUserDTO) {
        User user = User.builder()
                .email(newUserDTO.getEmail())
                .username(newUserDTO.getUsername())
                .password(newUserDTO.getPassword())
                .profilePicture(newUserDTO.getProfilePicture())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
