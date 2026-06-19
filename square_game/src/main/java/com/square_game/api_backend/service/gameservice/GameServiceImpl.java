package com.square_game.api_backend.service.gameservice;

import com.square_game.api_backend.dao.GameDao;
import com.square_game.api_backend.dto.GameCreationParameters;
import com.square_game.api_backend.gameplugin.GamePlugin;
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
        Game newGame = null;
        for (GamePlugin factory : plugins){
            if (factory.getFactoryId().equals(params.factoryId())){
                newGame = factory.createGame(params.boardSize());
                data.upsert(newGame);
                break;
            }
        }
        return newGame;
    }

    @Override
    public Optional<Game> getGame(UUID gameId) {
        return data.findById(gameId.toString());
    }

    @Override
    public Optional<GameStatus> getStatus(UUID gameId) {
        Optional<Game> game = data.findById(gameId.toString());
        return game.map(g-> g.getStatus());
    }

    @Override
    public Optional<Collection<Token>> getAvailableTokens (UUID gameId) {
        return  data.findById(gameId.toString()).map(g -> g.getRemainingTokens());

    }

    @Override
    public Optional<Game> playMove(UUID gameId, int x, int y) throws InvalidPositionException {
        CellPosition newPosition = new CellPosition(x, y);
        Game game = data.findById(gameId.toString()).orElse(null);
        if (game != (null)) {
            try {
                game.getRemainingTokens()
                        .stream()
                        .findFirst()
                        .get()
                        .moveTo(newPosition);

                data.upsert(game);

            } catch (InvalidPositionException e) {
                throw new InvalidPositionException(e.getMessage());
            }
        }
        return Optional.ofNullable(game);
    }
}
