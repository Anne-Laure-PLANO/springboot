package com.user.api_backend.services;

import com.user.api_backend.entities.User;
import com.user.api_backend.repositories.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JpaUserRepository repository;

    @Override
    public User createUser(String firstName, String lastName, String mail) {

        return null;
    }

    @Override
    public Optional<User> getUserById(UUID id) {

        return null;
    }

    @Override
    public Optional<User> updateUser(UUID id, String firstName, String lastName, String mail) {

        return null;
    }

    @Override
    public Optional<User> deleteUser(UUID id) {

        return null;
    }
}
