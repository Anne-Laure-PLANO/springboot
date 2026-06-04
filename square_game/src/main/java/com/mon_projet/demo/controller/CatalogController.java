package com.mon_projet.demo.controller;

import com.mon_projet.demo.service.gamecatalog.GameCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;

@RestController
public class CatalogController {

    @Autowired
    private GameCatalog gameCatalog;


    @GetMapping("/catalog")
    public Collection<String> getGameCatalog(Locale locale) {
        return gameCatalog.getAvailableGames(locale);
    }



}
