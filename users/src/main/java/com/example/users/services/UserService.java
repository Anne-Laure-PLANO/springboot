package com.example.users.services;

import com.example.users.User;
import com.example.users.dto.UserCreateParams;

import java.util.UUID;

public interface UserService {
    User createUser(UserCreateParams params);
    User getUserById(UUID id);
    User deleteUser(UUID id);
    User isUserExist(UUID id);
}
