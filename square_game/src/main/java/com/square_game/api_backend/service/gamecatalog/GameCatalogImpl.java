package com.square_game.api_backend.service.gamecatalog;


import com.square_game.api_backend.gameplugin.GamePlugin;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;


@Service
public class GameCatalogImpl implements GameCatalog {

    private final Collection<GamePlugin> factories;

    public GameCatalogImpl(Collection<GamePlugin> factories) {
        this.factories = factories;
    }

    @Override
    public Collection<String> getAvailableGames(Locale locale) {
        Collection<String> result = new ArrayList<>();
        for (GamePlugin factory : factories){
            result.add(factory.getName(locale));
        }
        return result;


    }
}
