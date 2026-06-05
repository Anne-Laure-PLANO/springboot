package com.example.users.controllers;

import com.example.users.User;
import com.example.users.dto.UserCreateParams;
import com.example.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userServiceImpl;

    @PostMapping("/")
    public User createUser(@RequestBody UserCreateParams params){
        return userServiceImpl.createUser(params);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id){
        return userServiceImpl.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable UUID id) {
        return userServiceImpl.deleteUser(id);
    }

    @GetMapping("/{id}/valid")
    public User isUserExist(@PathVariable UUID id){
        return userServiceImpl.isUserExist(id);
    }
}
