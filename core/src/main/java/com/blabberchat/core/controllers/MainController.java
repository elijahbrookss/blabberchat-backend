package com.blabberchat.core.controllers;


import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;


    @PostMapping("/users")
    public ResponseEntity<User> getUser(@Valid @RequestBody NewUserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

}
