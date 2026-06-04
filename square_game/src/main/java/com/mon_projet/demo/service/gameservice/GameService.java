package com.mon_projet.demo.service.gameservice;

import com.mon_projet.demo.dto.GameCreationParameters;
import fr.le_campus_numerique.square_games.engine.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


public interface GameService {

     Game createGame(GameCreationParameters params) ;

     Optional<Game> getGame(UUID gameId) ;

     Optional<GameStatus> getStatus(UUID gameId);

     Optional<Collection<Token>> getAvailableTokens (UUID gameId);

     Optional<Game> playMove(UUID gameId, int x, int y) throws InvalidPositionException;

    }
