package com.example.users.services;

import com.example.users.User;
import com.example.users.UserImpl;
import com.example.users.dto.UserCreateParams;
import com.example.users.entities.UserEntity;
import com.example.users.repositories.JpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final JpaUserRepository repository;

    public UserServiceImpl(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(UserCreateParams params) {
        UserEntity entity = new UserEntity(UUID.randomUUID(), params.firstName(), params.lastName());
        entity = repository.save(entity);
        return toUser(entity);
    }

    @Override
    public User getUserById(UUID id) {
        return repository.findById(id)
                .map(this::toUser)
                .orElse(null);
    }

    @Override
    public User deleteUser(UUID id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.deleteById(id);
                    return toUser(entity);
                })
                .orElse(null);
    }

    @Override
    public User isUserExist(UUID id) {
        return repository.findById(id)
                .map(this::toUser)
                .orElse(null);
    }

    private User toUser(UserEntity entity) {
        return new UserImpl(entity.getUserId(), entity.getFirstName(), entity.getLastName());
    }
}
