package com.mon_projet.demo.service.gamecatalog;


import java.util.Collection;
import java.util.Locale;

public interface GameCatalog {

    Collection<String> getAvailableGames(Locale locale);

}
