package com.uma.intelligentsystems.astar;

import com.uma.intelligentsystems.astar.MazeMovement;
import com.uma.intelligentsystems.astar.Node;
import com.uma.intelligentsystems.maze.Maze;
import com.uma.intelligentsystems.maze.Position;

import java.util.*;

public class AStar {
    private final Maze maze;
    private final MazeMovement[] movements;

    public AStar(Maze maze, MazeMovement[] movements) {
        this.maze = maze;
        this.movements = movements;
    }

    public Collection<Node> solve() {
        Position start = maze.getInitial();
        Position goal = maze.getGoal();

        Map<Position, Node> closedSet = new HashMap<>();
        Queue<Node> openSet = new PriorityQueue<>();
        Map<Position, Node> openSetMap = new HashMap<>();

        Node firstNode = new Node(start, 0, goal, movements);
        openSet.offer(firstNode);
        openSetMap.put(firstNode.getPosition(), firstNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if(current.isGoal()) {
                return current.toList();
            }

            closedSet.put(current.getPosition(), current);
            openSetMap.remove(current.getPosition());

            for (Node neighbor : current.generateNeighbors(maze)) {
                if(closedSet.containsKey(neighbor.getPosition())) continue;

                Node n = openSetMap.getOrDefault(neighbor.getPosition(), null);
                if(n == null || neighbor.g() < n.g()) {
                    // We need to remove it if we want to update its order
                    if (openSetMap.containsKey(neighbor.getPosition())) {
                        openSet.remove(neighbor);
                    }

                    openSetMap.put(neighbor.getPosition(), neighbor);
                    openSet.add(neighbor);
                }

                // horizontal axis: % of obstacles
                // vertical axis: min length of optimal path
                // vertical axis 2: number of times path is not reached
            }
        }
       return null;
    }
}
