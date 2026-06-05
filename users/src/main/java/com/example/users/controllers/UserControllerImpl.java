package com.example.users.controllers;


import com.example.users.User;
import com.example.users.dto.UserCreateParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller("/users")
public class UsersController {

    @PostMapping("/")
    public User createUser(@RequestBody UserCreateParams params){
        return null;
    }

    @GetMapping("/{id}")
    public User getUserById(@RequestParam UUID id){
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
