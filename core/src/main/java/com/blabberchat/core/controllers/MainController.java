package com.blabberchat.core.controllers;


import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.blabberchat.core.util.UtilClass.getUri;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<User> saveUser(@Valid @RequestBody NewUserDTO userDTO) {
        return ResponseEntity.created(getUri("/api/users")).body(userService.createUser(userDTO));
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

}
