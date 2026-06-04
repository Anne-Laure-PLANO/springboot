package com.mon_projet.demo.dao.jpa.entity;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "cell")
public class GameTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String ownerId;

    public String name;

    public boolean removed;

    public Integer x;

    public Integer y;
}