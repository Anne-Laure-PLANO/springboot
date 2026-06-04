package com.mon_projet.demo.gameplugin;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Locale;

public interface GamePlugin {


    String getFactoryId();

    Game createGame();

    Game createGame(int boardSize);


    String getName(Locale locale);

}
