package com.square_game.api_backend.service.gamecatalog;


import java.util.Collection;
import java.util.Locale;

public interface GameCatalog {

    Collection<String> getAvailableGames(Locale locale);

}
