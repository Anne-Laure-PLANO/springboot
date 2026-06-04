package com.mon_projet.demo.gameplugin;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Component
public class TaquinPlugin implements GamePlugin {

    private final GameFactory factory = new TaquinGameFactory();
    private final MessageSource messageSource;

    public TaquinPlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Value("${game.taquin.default-player-count}")
    private int playerCount;

    @Value("${game.taquin.default-board-size}")
    private int boardSize;


    @Override
    public String getFactoryId(){
        return factory.getGameFactoryId();
    }

    @Override
    public Game createGame(int boardSize){
        return factory.createGame(playerCount, boardSize);
    }

    @Override
    public Game createGame( ){
        return factory.createGame(playerCount,boardSize);
    }

    @Override
    public String getName(Locale locale){
        return messageSource.getMessage("game.taquin.name", null, locale);
    }

}
