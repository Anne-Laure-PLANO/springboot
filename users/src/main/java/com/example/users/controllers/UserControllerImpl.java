package com.example.users.controllers;


import com.example.users.entities.User;
import com.example.users.dto.UserCreateParams;
import com.example.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller("/users")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService service;

    @PostMapping("/")
    public User createUser(@RequestBody UserCreateParams params){
        return service.createUser(params.getFirstName(), params.getLastName(), params.getMail());
    }

    @GetMapping("/{id}")
    public User getUserById(@RequestParam UUID id){
        Optional<User> result = service.getUserById(id);

        return null;
    }

    @DeleteMapping("/{id}")
    public User deleteUser (@RequestParam UUID id) {

        return null;
    }

    @GetMapping("/{id}/valid")
    public User isUserExist(@RequestParam UUID id){

        return null;
    }
}
