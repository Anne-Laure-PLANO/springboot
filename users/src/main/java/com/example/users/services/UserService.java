package com.example.users.services;

import com.example.users.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {


    User createUser(String firstName, String lastName, String mail);

    Optional<User> getUserById(UUID id);

    Optional<User> updateUser(UUID id, String firstName, String lastName, String mail);

    Optional<User> deleteUser(UUID id);
}
