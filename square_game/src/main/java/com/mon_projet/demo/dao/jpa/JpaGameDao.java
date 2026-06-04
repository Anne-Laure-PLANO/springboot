package com.mon_projet.demo.dao.jpa;

import com.mon_projet.demo.dao.GameDao;
import com.mon_projet.demo.dao.jpa.entity.GameEntity;
import com.mon_projet.demo.dao.jpa.entity.GameTokenEntity;
import fr.le_campus_numerique.square_games.engine.*;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

@Repository
@Primary
public class JpaGameDao implements GameDao {

    private GameEntityRepository repository;


    public JpaGameDao(GameEntityRepository repository) {
        this.repository = repository;

    }

    @Override
    public Stream<Game> findAll() {

        repository.findAll();
        return Stream.empty();
    }

    @Override
    public Optional<Game> findById(String gameId) {
        UUID id = UUID.fromString(gameId);
        GameEntity gameEntity = repository.findById(id).orElseThrow();
        Optional<Game> game = toGame(gameEntity);
        return game;
    }

    @Override
    public Game upsert(Game game) {
        GameEntity gameEntity = toEntity(game);
        repository.save(gameEntity);
        return game;
    }

    @Override
    public void delete(String gameId) {

    }

    public Optional<Game> toGame(GameEntity gameEntity) {
        UUID id = gameEntity.getId();
        String factoryId = gameEntity.getFactoryId();
        int boardSize = gameEntity.getBoardSize();
        List<UUID> playerIds = Arrays.stream(gameEntity.getPlayerIds().split(","))
                .map(UUID::fromString)
                .toList();

        Collection<TokenPosition<UUID>> boardTokens = new ArrayList<>();
        Collection<TokenPosition<UUID>> boardRemovedTokens = new ArrayList<>();

        for (GameTokenEntity tokenEntity : gameEntity.getTokens()) {
            CellPosition position = new CellPosition(tokenEntity.x, tokenEntity.y);
            UUID ownerId = UUID.fromString(tokenEntity.ownerId);
            String name = tokenEntity.name;
            if (tokenEntity.removed){
                boardRemovedTokens.add(new TokenPosition<>(ownerId, name, position.x(),position.y()));
            } else {
                boardTokens.add(new TokenPosition<>(ownerId, name, position.x(), position.y()));
            }
        }


        Game game = null;
        switch (factoryId) {
            case "tictactoe" :
                try {
                    game = new TicTacToeGameFactory().createGameWithIds(id, boardSize,playerIds,boardTokens, boardRemovedTokens);
                } catch (InconsistentGameDefinitionException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "connect4" :
                try {
                    game = new ConnectFourGameFactory().createGameWithIds(id,boardSize,playerIds,boardTokens, boardRemovedTokens);
                } catch (InconsistentGameDefinitionException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "15 puzzle" :
                try {
                    game = new TaquinGameFactory().createGameWithIds(id, boardSize, playerIds, boardTokens, boardRemovedTokens);
                } catch (InconsistentGameDefinitionException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        assert game != null;
        return Optional.of(game);


    }


    public GameEntity toEntity (Game game){
        UUID id = game.getId();
        String factoryId = game.getFactoryId();
        int boardSize = game.getBoardSize();
        String playerIds ="";
        for (UUID player : game.getPlayerIds()){
            playerIds += player +",";
        }
        if (!playerIds.isEmpty()) {
            playerIds = playerIds.substring(0, playerIds.length() - 1);
        }

        List<GameTokenEntity> tokens = new ArrayList<>();
        for (Token token : game.getBoard().values()){
            tokens.add(toTokenEntity(token));
        }

        return new GameEntity(id, factoryId, boardSize, playerIds, tokens);
    }


    private GameTokenEntity toTokenEntity(Token token) {

        GameTokenEntity tokenEntity = new GameTokenEntity();

        tokenEntity.ownerId = token.getOwnerId().toString();
        tokenEntity.name = token.getName();

        CellPosition position = token.getPosition();
        tokenEntity.x = position.x();
        tokenEntity.y = position.y();

        return tokenEntity;
    }
}
