package com.uma.intelligentsystems;

public enum Cell {
    EMPTY(' '),
    OBSTACLE('.'),
    GOAL('G'),
    INITIAL('I'),
    OPTIMAL('X'),
    OPTIMAL_ALTERNATIVE1('1'),
    OPTIMAL_ALTERNATIVE2('2');

    private char representation;

    Cell(char representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return "" + representation;
    }
}
