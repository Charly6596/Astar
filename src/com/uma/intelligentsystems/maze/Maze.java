package com.uma.intelligentsystems;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Maze {
    private final int rows;
    private final int columns;
    private final double ratioObstacles;

    private final Random rand;

    private final Cell[][] maze;

    private Position initial;
    private Position goal;

    public Maze(int rows, int columns, double ratioObstacles, Random rand) {
        this.rand = rand;
        this.rows = rows;
        this.columns = columns;
        this.ratioObstacles = ratioObstacles;
        maze = new Cell[rows][columns];
        randomize();
    }

    public void applyPath(Collection<Node> path) {
        applyPath(path, Cell.OPTIMAL);
    }
    public void applyPath(Collection<Node> path, Cell pathMark) {
        for(Node n : path) {
            Position p = n.getPosition();
            if(!p.equals(goal) && !p.equals(initial)) {
                set(p.i, p.j, pathMark);
            }
        }
    }

    private void randomize() {
        clear();
        int numberOfElements = (int) Math.floor(rows * columns * ratioObstacles + 2);
        int currentRow = 0;
        int currentColumn = 0;
        for (int i = 0; i < numberOfElements; i++) {

            currentRow = rand.nextInt(rows);
            currentColumn = rand.nextInt(columns);

            {
                while (maze[currentRow][currentColumn] != Cell.EMPTY) {
                    currentRow = rand.nextInt(rows);
                    currentColumn = rand.nextInt(columns);
                }

                if (i == numberOfElements - 1) {
                    set(currentRow, currentColumn, Cell.GOAL);
                    goal = new Position(currentRow, currentColumn);
                } else if (i == numberOfElements - 2) {
                    set(currentRow, currentColumn, Cell.INITIAL);
                    initial = new Position(currentRow, currentColumn);
                } else {
                    set(currentRow, currentColumn, Cell.OBSTACLE);
                }
            }
        }
    }

    public void set(int r, int c, Cell cell) {
        if (r >= 0 && c >= 0 && r < rows && c < columns) {
            maze[r][c] = cell;
        }
    }

    public Cell get(int r, int c) {
        if (r >= 0 && r < this.rows && c >= 0 && c < this.columns) {
            return maze[r][c];
        }

        return Cell.OBSTACLE;
    }

    public void clear() {
        for (Cell[] cells : maze) {
            Arrays.fill(cells, Cell.EMPTY);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('|');
        sb.append("-".repeat(columns));
        sb.append('|');
        sb.append('\n');
        for (int x = 0; x < rows; x++) {
            sb.append('|');
            for (int y = 0; y < columns; y++) {
                sb.append(maze[x][y]);
            }
            sb.append("|\n");
        }

        sb.append("|");
        sb.append("-".repeat(columns));
        sb.append("|\n");
        return sb.toString();
    }

    public Position getInitial() {
        return initial;
    }

    public Position getGoal() {
        return goal;
    }
}
