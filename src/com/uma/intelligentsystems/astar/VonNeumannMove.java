package com.uma.intelligentsystems.astar;

public enum VonNeumannMove implements MazeMovement {
    UP(1, 0),
    RIGHT(0,1),
    DOWN(-1, 0),
    LEFT(0, -1);

    private final int x;
    private final int y;

    VonNeumannMove(int x, int y) {
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
        return "Von Neumann movement";
    }
}
