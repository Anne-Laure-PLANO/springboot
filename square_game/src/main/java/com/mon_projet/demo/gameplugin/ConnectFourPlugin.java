package com.mon_projet.demo.gameplugin;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ConnectFourPlugin implements GamePlugin {

    private final GameFactory factory =
            new ConnectFourGameFactory();
    private final MessageSource messageSource;

    public ConnectFourPlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Value("${game.connectfour.default-player-count}")
    private int playerCount;

    @Value("${game.connectfour.default-board-size}")
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
    public Game createGame(){
        return factory.createGame(playerCount,boardSize);
    }

    @Override
    public String getName(Locale locale){
        return messageSource.getMessage("game.connectfour.name", null, locale);
    }

}
