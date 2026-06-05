package com.mon_projet.demo.controller;

import com.mon_projet.demo.service.gamecatalog.GameCatalog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameCatalog gameCatalog;

    @Test
    void getCatalog_shouldReturnGameNames() throws Exception {
        when(gameCatalog.getAvailableGames(any(Locale.class)))
                .thenReturn(List.of("Tic-Tac-Toe", "Puissance 4"));

        mockMvc.perform(get("/catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Tic-Tac-Toe"))
                .andExpect(jsonPath("$[1]").value("Puissance 4"));
    }

    @Test
    void getCatalog_empty_shouldReturnEmptyArray() throws Exception {
        when(gameCatalog.getAvailableGames(any(Locale.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
