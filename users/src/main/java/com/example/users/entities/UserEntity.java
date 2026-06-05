package com.example.users.entities;

import com.example.users.domain.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;
    private String mail;


    public UserEntity(){
    }

    public UserEntity(String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public UserEntity(UUID userId, String firstName, String lastName, String mail) {
        this.id = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;

    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
