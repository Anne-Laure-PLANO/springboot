package com.example.users.repositories;

import com.example.users.entities.User;
import com.example.users.entities.UserRepository;
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
