package com.uma.intelligentsystems;

public enum MooreMove implements MazeMovement {
    UP ( 1,  0),
    UP_RIGHT(1, 1),
    RIGHT( 0,  1),
    DOWN_RIGHT(-1, 1),
    DOWN (-1,  0),
    DOWN_LEFT(-1, -1),
    WEST  ( 0, -1),
    NORTH_WEST(1, -1);

    private int x;
    private int y;
    MooreMove(int x, int y) {
       this.x = x;
       this.y = y;
    }

    @Override
    public int incX() {
        return x;
    }

    @Override
    public int incY() {
        return y;
    }

    @Override
    public String getName() {
        return "Moore movement";
    }
}
