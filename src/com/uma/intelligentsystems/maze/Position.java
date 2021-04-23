package com.uma.intelligentsystems.maze;

import java.util.Objects;

public class Position {
    public final int i;
    public final int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int manhattanDistanceTo(Position p) {
        return Math.abs(p.i - this.i) + Math.abs(p.j - this.j);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return i == position.i && j == position.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
