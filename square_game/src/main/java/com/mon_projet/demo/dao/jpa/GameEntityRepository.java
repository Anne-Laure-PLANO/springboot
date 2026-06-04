package com.mon_projet.demo.dao.jpa;

import com.mon_projet.demo.dao.jpa.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameEntityRepository extends JpaRepository<GameEntity, UUID> {

}
