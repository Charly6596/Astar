package com.uma.intelligentsystems;

import java.util.Collection;

public class Main {
    static final int ROWS = 60;
    static final int COLUMNS = 80;
    static final double OBSTACLES = 0.3;

    public static void main(String[] args) {
        Maze maze = new Maze(ROWS, COLUMNS, OBSTACLES);
        System.out.print(maze);
        AStar as = new AStar(maze, VonnNeumanMove.values());
        Collection<Node> result = as.solve();
        if(result == null) {
            System.out.println("No solution found");
           return;
        }
        for(Node n : result) {
            int i = n.getPosition().i;
            int j = n.getPosition().j;
            Cell cell = maze.get(i, j);
            if(cell == Cell.GOAL || cell == Cell.INITIAL) continue;
            maze.set(i, j, Cell.OPTIMAL);
        }

        System.out.print(maze);
    }
}
