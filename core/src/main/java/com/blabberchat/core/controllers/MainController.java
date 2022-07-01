package com.blabberchat.core.controllers;


import com.blabberchat.core.dtos.NewUserDTO;
import com.blabberchat.core.models.User;
import com.blabberchat.core.services.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/users")
    public User getUser(@Valid @RequestBody NewUserDTO userDTO) {
        return mainService.createNewUser(userDTO);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return mainService.getAllUsers();
    }


}
