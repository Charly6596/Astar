package com.uma.intelligentsystems.maze;

import java.util.Objects;

public class Position {
    public final int i;
    public final int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public double manhattanDistanceTo(Position p) {
        return Math.abs(p.i - this.i) + Math.abs(p.j - this.j);
    }

    public double euclideanDistanceTo(Position p) {
        return Math.sqrt(Math.pow(p.i - this.i, 2) + Math.pow(p.j - this.j, 2));
    }

    public double chebyshevDistanceTo(Position p){
        return Math.max(Math.abs(this.i - p.i), Math.abs(this.j - p.j));
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
