package com.mon_projet.demo.controller;


import com.mon_projet.demo.dto.GameCreationParameters;
import com.mon_projet.demo.dto.MoveParams;
import com.mon_projet.demo.exception.GameNotFoundException;
import com.mon_projet.demo.service.gameservice.GameService;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;


@RestController
public class GameController {

    @Autowired
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public Game createGame(@RequestBody GameCreationParameters params) {
        return gameService.createGame(params);

    }

    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable UUID gameId) {
            return gameService.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
    }

    @GetMapping("/games/{gameId}/status")
    public GameStatus getStatus (@PathVariable UUID gameId) {
            return gameService.getStatus(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

    }

    @GetMapping ("/games/{gameId}/tokens")
    public Collection<Token> getRemainingTokens (@PathVariable UUID gameId)  {
        return gameService.getAvailableTokens(gameId).orElseThrow(()-> new GameNotFoundException(gameId));
}

    @PostMapping("/games/{gameId}/moves")
    public Game playMove(
            @PathVariable UUID gameId,
            @RequestBody MoveParams params
    ) throws InvalidPositionException {
        try {
            return gameService.playMove(gameId, params.getX(), params.getY()).orElseThrow(()-> new GameNotFoundException(gameId));
        } catch (InvalidPositionException e) {
            throw new InvalidPositionException(e.getMessage());
        }
    }


}
