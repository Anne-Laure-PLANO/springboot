package com.mon_projet.demo.dao;

import fr.le_campus_numerique.square_games.engine.Game;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class InMemoryGameDao implements GameDao  {
    private final HashMap<@NotNull String, Game> data;

    public  InMemoryGameDao(){
        this.data = new HashMap<>();
    }





    public Game upsert(Game game)  {
        data.put(game.getId().toString(), game);
        return game;
    }

    public Optional<Game> findById(String gameId) {
        return Optional.ofNullable(data.get(gameId));
    }

    public Stream<Game> findAll() {
        return data.values().stream();
    }

    @Override
    public void delete(String gameId) {
        data.remove(gameId);
    }

}
