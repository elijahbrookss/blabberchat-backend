package com.blabberchat.core.services;


import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.dtos.RoleDTO;
import com.blabberchat.core.models.Role;
import com.blabberchat.core.models.User;
import com.blabberchat.core.repositories.RoleRepository;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        log.info("User found in database: {}", username);

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).toList();

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

    public Role createRole(String name) {
        log.info("Create role: {}", name);
        return roleRepository.save(Role.builder().name(name).build());
    }

    // Convert List<RoleDTO> to List<Role>
    private List<Role> convertRoleDTOtoRole(List<RoleDTO> roleDTOS) {
        log.info("Converting RoleDTO to DTO, RoleDTOs: {}", roleDTOS);

        return roleDTOS.stream()
                .map(roleDTO ->
                    roleRepository.findByName(roleDTO.getName())
                           .orElse(roleRepository.save(Role.builder().name(roleDTO.getName()).build()))
                ).collect(Collectors.toList());
    }
}







