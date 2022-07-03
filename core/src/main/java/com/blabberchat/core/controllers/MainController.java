package com.blabberchat.core.controllers;


import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")

@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody NewUserDTO userDTO) {
        return ResponseEntity.created(getUri("/api/users")).body(userService.createUser(userDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }


    private URI getUri(String uriPath) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(uriPath).toUriString());
    }

}
