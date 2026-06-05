package com.mon_projet.demo.service.gameservice;

import com.mon_projet.demo.dao.GameDao;
import com.mon_projet.demo.dto.GameCreationParameters;
import com.mon_projet.demo.gameplugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GamePlugin plugin;
    @Mock
    private GameDao dao;
    @Mock
    private Game game;
    @Mock
    private Token token;

    private GameServiceImpl service;
    private final UUID gameId = UUID.randomUUID();
    private final String factoryId = "test-factory";

    @BeforeEach
    void setUp() {
        service = new GameServiceImpl(List.of(plugin), dao);
    }

    @Test
    void createGame_withPlayerCount_shouldCreateAndPersist() {
        when(plugin.getFactoryId()).thenReturn(factoryId);
        int playerCount = 2;
        int boardSize = 3;
        when(plugin.createGame(playerCount, boardSize)).thenReturn(game);
        GameCreationParameters params = new GameCreationParameters(factoryId, playerCount, boardSize);

        Game result = service.createGame(params);

        assertSame(game, result);
        verify(plugin).createGame(playerCount, boardSize);
        verify(dao).upsert(game);
    }

    @Test
    void createGame_withoutPlayerCount_shouldFallbackToBoardSize() {
        when(plugin.getFactoryId()).thenReturn(factoryId);
        int boardSize = 5;
        when(plugin.createGame(boardSize)).thenReturn(game);
        GameCreationParameters params = new GameCreationParameters(factoryId, null, boardSize);

        Game result = service.createGame(params);

        assertSame(game, result);
        verify(plugin).createGame(boardSize);
        verify(dao).upsert(game);
    }

    @Test
    void createGame_unknownFactoryId_shouldThrow() {
        when(plugin.getFactoryId()).thenReturn(factoryId);
        GameCreationParameters params = new GameCreationParameters("unknown", 2, 3);

        assertThrows(IllegalArgumentException.class, () -> service.createGame(params));
        verifyNoInteractions(dao);
    }

    @Test
    void getGame_existingId_shouldReturnGame() {
        when(dao.findById(gameId.toString())).thenReturn(Optional.of(game));

        Optional<Game> result = service.getGame(gameId);

        assertTrue(result.isPresent());
        assertSame(game, result.get());
    }

    @Test
    void getGame_unknownId_shouldReturnEmpty() {
        when(dao.findById(gameId.toString())).thenReturn(Optional.empty());

        Optional<Game> result = service.getGame(gameId);

        assertFalse(result.isPresent());
    }

    @Test
    void getStatus_existingId_shouldReturnStatus() {
        when(dao.findById(gameId.toString())).thenReturn(Optional.of(game));
        when(game.getStatus()).thenReturn(GameStatus.ONGOING);

        Optional<GameStatus> result = service.getStatus(gameId);

        assertTrue(result.isPresent());
        assertEquals(GameStatus.ONGOING, result.get());
    }

    @Test
    void getStatus_unknownId_shouldReturnEmpty() {
        when(dao.findById(gameId.toString())).thenReturn(Optional.empty());

        Optional<GameStatus> result = service.getStatus(gameId);

        assertFalse(result.isPresent());
    }

    @Test
    void getAvailableTokens_existingId_shouldReturnTokens() {
        when(dao.findById(gameId.toString())).thenReturn(Optional.of(game));
        when(game.getRemainingTokens()).thenReturn(List.of(token));

        Optional<Collection<Token>> result = service.getAvailableTokens(gameId);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertSame(token, result.get().iterator().next());
    }

    @Test
    void getAvailableTokens_unknownId_shouldReturnEmpty() {
        when(dao.findById(gameId.toString())).thenReturn(Optional.empty());

        Optional<Collection<Token>> result = service.getAvailableTokens(gameId);

        assertFalse(result.isPresent());
    }

    @Test
    void playMove_gameFound_shouldMoveTokenAndPersist() throws Exception {
        int x = 1, y = 2;
        when(dao.findById(gameId.toString())).thenReturn(Optional.of(game));
        when(game.getRemainingTokens()).thenReturn(List.of(token));

        Optional<Game> result = service.playMove(gameId, x, y);

        assertTrue(result.isPresent());
        assertSame(game, result.get());
        verify(token).moveTo(new CellPosition(x, y));
        verify(dao).upsert(game);
    }

    @Test
    void playMove_gameNotFound_shouldReturnEmpty() throws Exception {
        when(dao.findById(gameId.toString())).thenReturn(Optional.empty());

        Optional<Game> result = service.playMove(gameId, 1, 2);

        assertFalse(result.isPresent());
        verifyNoMoreInteractions(token, dao);
    }

    @Test
    void playMove_noTokensAvailable_shouldThrow() {
        when(dao.findById(gameId.toString())).thenReturn(Optional.of(game));
        when(game.getRemainingTokens()).thenReturn(List.of());

        assertThrows(IllegalStateException.class, () -> service.playMove(gameId, 1, 2));
    }
}
