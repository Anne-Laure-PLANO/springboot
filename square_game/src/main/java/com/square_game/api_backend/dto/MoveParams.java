package com.square_game.api_backend.dto;

import fr.le_campus_numerique.square_games.engine.CellPosition;

public class MoveParams {
    private int x ;
    private int y ;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
