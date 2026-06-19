package com.user.api_backend.repositories;

import com.user.api_backend.entities.User;
import com.user.api_backend.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRepositoryImpl implements JpaUserRepository{

    @Autowired
    private UserRepository repository;

    public User createUser(User user){
        repository.save(toUserEntity(user));
        return user;
    }

    private User toUserEntity(User user) {
        return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getMail());

    }


}
