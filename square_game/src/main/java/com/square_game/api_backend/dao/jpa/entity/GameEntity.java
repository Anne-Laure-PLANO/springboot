package com.square_game.api_backend.dao.jpa.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String factoryId;

    private int boardSize;

    private String playerIds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_game")
    private List<GameTokenEntity> tokens;


    public GameEntity() {} // Obligatoire pour JPA

    public GameEntity(UUID id, String factoryId, int boardSize, String playerIds, List<GameTokenEntity> tokens) {
        this.id = id;
        this.factoryId = factoryId;
        this.boardSize = boardSize;
        this.playerIds = playerIds;
        this.tokens = tokens;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public String getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(String playerIds) {
        this.playerIds = playerIds;
    }

    public List<GameTokenEntity> getTokens() {
        return tokens;
    }

    public void setTokens(List<GameTokenEntity> tokens) {
        this.tokens = tokens;
    }
}