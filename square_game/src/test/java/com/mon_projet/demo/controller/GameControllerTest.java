package com.mon_projet.demo.controller;

import com.mon_projet.demo.dto.GameCreationParameters;
import com.mon_projet.demo.dto.MoveParams;
import com.mon_projet.demo.exception.GameNotFoundException;
import com.mon_projet.demo.service.gameservice.GameService;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;
import fr.le_campus_numerique.square_games.engine.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController controller;

    @Test
    void createGame_shouldDelegateToService() {
        GameCreationParameters params = new GameCreationParameters("tictactoe", 2, 3);
        Game game = mock(Game.class);
        when(gameService.createGame(params)).thenReturn(game);

        Game result = controller.createGame(params);

        assertSame(game, result);
    }

    @Test
    void getGame_existingId_shouldReturnGame() {
        UUID id = UUID.randomUUID();
        Game game = mock(Game.class);
        when(gameService.getGame(id)).thenReturn(Optional.of(game));

        Game result = controller.getGame(id);

        assertSame(game, result);
    }

    @Test
    void getGame_unknownId_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(gameService.getGame(id)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> controller.getGame(id));
    }

    @Test
    void getStatus_existingId_shouldReturnStatus() {
        UUID id = UUID.randomUUID();
        when(gameService.getStatus(id)).thenReturn(Optional.of(GameStatus.ONGOING));

        GameStatus result = controller.getStatus(id);

        assertEquals(GameStatus.ONGOING, result);
    }

    @Test
    void getStatus_unknownId_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(gameService.getStatus(id)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> controller.getStatus(id));
    }

    @Test
    void getTokens_existingId_shouldReturnTokens() {
        UUID id = UUID.randomUUID();
        Token token = mock(Token.class);
        when(gameService.getAvailableTokens(id)).thenReturn(Optional.of(List.of(token)));

        Collection<Token> result = controller.getRemainingTokens(id);

        assertEquals(1, result.size());
        assertSame(token, result.iterator().next());
    }

    @Test
    void getTokens_unknownId_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(gameService.getAvailableTokens(id)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> controller.getRemainingTokens(id));
    }

    @Test
    void playMove_existingId_shouldReturnUpdatedGame() throws Exception {
        UUID id = UUID.randomUUID();
        MoveParams params = new MoveParams();
        params.setX(1);
        params.setY(2);
        Game game = mock(Game.class);
        when(gameService.playMove(id, 1, 2)).thenReturn(Optional.of(game));

        Game result = controller.playMove(id, params);

        assertSame(game, result);
    }

    @Test
    void playMove_unknownId_shouldThrow() throws Exception {
        UUID id = UUID.randomUUID();
        MoveParams params = new MoveParams();
        params.setX(1);
        params.setY(2);
        when(gameService.playMove(id, 1, 2)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> controller.playMove(id, params));
    }
}
