package com.uma.intelligentsystems;

public enum VonnNeumanMove implements MazeMovement {
    UP(1, 0),
    RIGHT(0,1),
    DOWN(-1, 0),
    LEFT(0, -1);

    private int x;
    private int y;

    VonnNeumanMove(int x, int y) {
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
}
