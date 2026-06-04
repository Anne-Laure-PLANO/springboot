package com.mon_projet.demo.dao;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

@Repository
public class JdbcGameDao implements GameDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;



    public JdbcGameDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
// cette classe va contenir les requêtes SQL.

    public Stream<Game> findAll() {
        return null;
    }

    public Optional<Game> findById(String gameId) {

        String sql = """
                SELECT * FROM game
                WHERE id = :gameId
                """;


    return null;

    }
    public Game upsert (Game game){

        UUID idGame = game.getId();
       int boardSize = game.getBoardSize();
       UUID playerA = game.getPlayerIds().stream().toList().get(0);
       UUID playerB = game.getPlayerIds().stream().toList().get(1);
       Map<CellPosition, Token> tokens = game.getBoard();

       String sql = """
               INSERT INTO game (id, board_size, player_a, player_b)
               VALUES (:idGame, :boardSize, :playerA, :playerB)
               ON CONFLICT (id) DO UPDATE SET board_size = :boardSize, player_a = :playerA, player_b = :playerB
               
               """;

       MapSqlParameterSource params = new MapSqlParameterSource();

       params.addValue("idGame", idGame);
       params.addValue("boardSize", boardSize);
       params.addValue("playerA", playerA);
       params.addValue("playerB", playerB);

       jdbcTemplate.update(sql, params);
        String sqlToken = """
               INSERT INTO cell ( x, y, owner_id, name, id_game )
               VALUES ( :x, :y, :ownerId, :name, :idGame)
               ON CONFLICT (id) DO UPDATE SET  x = :x , y = :y , owner_id = :ownerId  , name = :name, id_game = :idGame
               """;

       for (Map.Entry<CellPosition, Token> entry : tokens.entrySet()){
           CellPosition position = entry.getKey();
           Token token = entry.getValue();

           int x = position.x();
           int y = position.y();
           UUID ownerId = token.getOwnerId().orElse(null);
           String name = token.getName();


           MapSqlParameterSource paramsToken = new MapSqlParameterSource();
           paramsToken.addValue("x", x);
           paramsToken.addValue("y", y);
           paramsToken.addValue("ownerId", ownerId);
           paramsToken.addValue("name", name);
           paramsToken.addValue("idGame", idGame); //idGame récupéré plus haut

           jdbcTemplate.update(sqlToken, paramsToken);
       }
       return game;
    }

    public void delete (String gameId){

    }

}