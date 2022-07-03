package com.blabberchat.core.services;


import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.dtos.RoleDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        log.info("User found in database: {}", username);

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new).toList();

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), simpleGrantedAuthorities);
    }
    public User createUser(NewUserDTO newUserDTO) {
        User user = User.builder()
                .email(newUserDTO.getEmail())
                .username(newUserDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(newUserDTO.getPassword()))
                .profilePicture(newUserDTO.getProfilePicture())
                .createdAt(LocalDateTime.now())
                .channels(new ArrayList<>())
                .roles(convertRoleDTOtoRole(newUserDTO.getRoles()))
                .build();

        userRepository.save(user);

        log.info("New user saved: {}", user.getUsername());
        return user;
    }

    @PreAuthorize("#username == authentication.principal")
    public User getUser(String username) {
        log.info("Fetching user: {}", username);
        return userRepository.findByUsername(username).orElseThrow();
    }

    @PreAuthorize("hasAuthority('USER_ROLE') or hasAuthority('USER_ADMIN')")
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    // Convert List<RoleDTO> to List<Role>
    private Set<String> convertRoleDTOtoRole(List<RoleDTO> roleDTOS) {
        log.info("Converting RoleDTO to DTO, RoleDTOs: {}", roleDTOS);
        return roleDTOS.stream().map(RoleDTO::getName).collect(Collectors.toSet());
    }
}







