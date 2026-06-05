package com.mon_projet.demo.dao;

import fr.le_campus_numerique.square_games.engine.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryGameDaoTest {

    private InMemoryGameDao dao;
    private Game game;
    private final UUID gameId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        dao = new InMemoryGameDao();
        game = mock(Game.class);
        when(game.getId()).thenReturn(gameId);
    }

    @Test
    void upsert_shouldStoreGame() {
        Game result = dao.upsert(game);

        assertSame(game, result);
        Optional<Game> found = dao.findById(gameId.toString());
        assertTrue(found.isPresent());
        assertSame(game, found.get());
    }

    @Test
    void findById_existingId_shouldReturnGame() {
        dao.upsert(game);

        Optional<Game> result = dao.findById(gameId.toString());

        assertTrue(result.isPresent());
        assertSame(game, result.get());
    }

    @Test
    void findById_unknownId_shouldReturnEmpty() {
        Optional<Game> result = dao.findById(UUID.randomUUID().toString());

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_shouldReturnAllGames() {
        Game game2 = mock(Game.class);
        when(game2.getId()).thenReturn(UUID.randomUUID());
        dao.upsert(game);
        dao.upsert(game2);

        var result = dao.findAll().collect(Collectors.toSet());

        assertEquals(2, result.size());
        assertTrue(result.contains(game));
        assertTrue(result.contains(game2));
    }

    @Test
    void findAll_empty_shouldReturnEmptyStream() {
        var result = dao.findAll().collect(Collectors.toSet());

        assertTrue(result.isEmpty());
    }

    @Test
    void delete_existingId_shouldRemoveGame() {
        dao.upsert(game);
        dao.delete(gameId.toString());

        Optional<Game> result = dao.findById(gameId.toString());
        assertFalse(result.isPresent());
    }

    @Test
    void delete_unknownId_shouldDoNothing() {
        dao.upsert(game);
        dao.delete(UUID.randomUUID().toString());

        Optional<Game> result = dao.findById(gameId.toString());
        assertTrue(result.isPresent());
    }

    @Test
    void upsert_shouldReplaceExistingGame() {
        dao.upsert(game);
        Game updated = mock(Game.class);
        when(updated.getId()).thenReturn(gameId);
        dao.upsert(updated);

        Optional<Game> result = dao.findById(gameId.toString());
        assertTrue(result.isPresent());
        assertSame(updated, result.get());
    }
}
