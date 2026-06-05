package com.example.users;

import java.util.UUID;

public class UserImpl implements User{

    private UUID id;
    private String firstName;
    private String lastName;

    public UserImpl (UUID id, String firstName, String lastName){
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //pour création d'un nouveau joueur
    public UserImpl(String firstName, String lastName){
        this.id= UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
