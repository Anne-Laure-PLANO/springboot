package com.mon_projet.demo.service.gamecatalog;

import com.mon_projet.demo.gameplugin.GamePlugin;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameCatalogImplTest {

    private final GamePlugin plugin1 = mock(GamePlugin.class);
    private final GamePlugin plugin2 = mock(GamePlugin.class);

    @Test
    void getAvailableGames_shouldReturnNamesFromAllPlugins() {
        when(plugin1.getName(Locale.FRENCH)).thenReturn("Tic-Tac-Toe");
        when(plugin2.getName(Locale.FRENCH)).thenReturn("Puissance 4");
        var catalog = new GameCatalogImpl(List.of(plugin1, plugin2));

        Collection<String> result = catalog.getAvailableGames(Locale.FRENCH);

        assertTrue(result.contains("Tic-Tac-Toe"));
        assertTrue(result.contains("Puissance 4"));
        assertEquals(2, result.size());
    }

    @Test
    void getAvailableGames_emptyPlugins_shouldReturnEmptyList() {
        var catalog = new GameCatalogImpl(List.of());

        Collection<String> result = catalog.getAvailableGames(Locale.ENGLISH);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAvailableGames_shouldPassLocaleToPlugins() {
        when(plugin1.getName(Locale.ENGLISH)).thenReturn("Tic-Tac-Toe");
        var catalog = new GameCatalogImpl(List.of(plugin1));

        catalog.getAvailableGames(Locale.ENGLISH);

        verify(plugin1).getName(Locale.ENGLISH);
    }
}
