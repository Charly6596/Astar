package com.uma.intelligentsystems;

import java.util.*;

public class AStar {
    private Maze maze;
    private MazeMovement[] movements;

    public AStar(Maze maze, MazeMovement[] movements) {
        this.maze = maze;
        this.movements = movements;
    }

    public Collection<Node> solve() {
        Position start = maze.getInitial();
        Position goal = maze.getGoal();

        Set<Node> closedSet = new HashSet<>();
        Queue<Node> openSet = new PriorityQueue<>();

        Node firstNode = new Node(start, 0, goal, movements);
        openSet.offer(firstNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if(current.isGoal()) {
                return current.toList();
            }

            closedSet.add(current);
            for (Node neighbor : current.generateNeighbors(maze)) {
                if(closedSet.contains(neighbor)) continue;

                if(!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }
            }
        }
       return null;
    }
}
