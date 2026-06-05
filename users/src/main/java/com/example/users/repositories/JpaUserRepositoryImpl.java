package com.example.users.repositories;

import com.example.users.domain.User;
import com.example.users.entities.UserEntity;
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

    private UserEntity toUserEntity(User user) {
        return new UserEntity(user.getId(), user.getFirstName(), user.getLastName(), user.getMail());

    }


}
