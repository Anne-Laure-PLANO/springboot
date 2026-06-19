package com.user.api_backend.services;

import com.user.api_backend.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {


    User createUser(String firstName, String lastName, String mail);

    Optional<User> getUserById(UUID id);

    Optional<User> updateUser(UUID id, String firstName, String lastName, String mail);

    Optional<User> deleteUser(UUID id);
}
