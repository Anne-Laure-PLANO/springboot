package com.mon_projet.demo.gameplugin;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TicTacToePlugin implements GamePlugin {

    private final GameFactory factory =
            new TicTacToeGameFactory();
    private final MessageSource messageSource;

    @Value("${game.tictactoe.default-player-count}")
    private int playerCount;

    @Value("${game.tictactoe.default-board-size}")
    private int boardSize;

    public TicTacToePlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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
        return factory.createGame(playerCount, boardSize);
    }

    @Override
    public String getName(Locale locale){
        return messageSource.getMessage("game.tictactoe.name", null, locale);
    }

}
