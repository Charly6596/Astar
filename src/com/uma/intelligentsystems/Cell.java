package com.uma.intelligentsystems;

public enum Cell {
    EMPTY(' '),
    OBSTACLE('.'),
    GOAL('G'),
    INITIAL('I'),
    OPTIMAL('X');

    private char representation;

    Cell(char representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return "" + representation;
    }
}
