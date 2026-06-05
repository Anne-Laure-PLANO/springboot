package com.mon_projet.demo.service.gameservice;

import com.mon_projet.demo.dao.GameDao;
import com.mon_projet.demo.dto.GameCreationParameters;
import com.mon_projet.demo.gameplugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.*;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class GameServiceImpl implements GameService {
    private final List<GamePlugin> plugins;
    private final GameDao data;

    public GameServiceImpl (List<GamePlugin> plugins, GameDao data){

        this.plugins = plugins;
        this.data = data;
    }

    @Override
    public Game createGame(GameCreationParameters params) {
        for (GamePlugin factory : plugins){
            if (factory.getFactoryId().equals(params.factoryId())){
                Game newGame = (params.playerCount() != null)
                        ? factory.createGame(params.playerCount(), params.boardSize())
                        : factory.createGame(params.boardSize());
                data.upsert(newGame);
                return newGame;
            }
        }
        throw new IllegalArgumentException("Unknown factoryId: " + params.factoryId());
    }

    @Override
    public Optional<Game> getGame(UUID gameId) {
        return data.findById(gameId.toString());
    }

    @Override
    public Optional<GameStatus> getStatus(UUID gameId) {
        return data.findById(gameId.toString()).map(Game::getStatus);
    }

    @Override
    public Optional<Collection<Token>> getAvailableTokens (UUID gameId) {
        return  data.findById(gameId.toString()).map(Game::getRemainingTokens);

    }

    @Override
    public Optional<Game> playMove(UUID gameId, int x, int y) throws InvalidPositionException {
        CellPosition newPosition = new CellPosition(x, y);
        Optional<Game> optGame = data.findById(gameId.toString());
        if (optGame.isEmpty()) {
            return Optional.empty();
        }
        Game game = optGame.get();
        Token token = game.getRemainingTokens()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No tokens available to move"));
        token.moveTo(newPosition);
        data.upsert(game);
        return Optional.of(game);
    }
}
