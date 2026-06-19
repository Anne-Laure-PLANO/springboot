package com.square_game.api_backend.dao.jpa;

import com.square_game.api_backend.dao.jpa.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameEntityRepository extends JpaRepository<GameEntity, UUID> {

}
