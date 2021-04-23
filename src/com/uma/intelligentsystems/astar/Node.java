package com.uma.intelligentsystems.astar;

import com.uma.intelligentsystems.maze.Position;
import com.uma.intelligentsystems.maze.Cell;
import com.uma.intelligentsystems.maze.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
    private final Position goal;
    private final Position pos;
    private final double g;
    private final double h;
    private final Node parent;
    private final MazeMovement[] movements;

    public Node(Position pos, double g, Position goal, MazeMovement[] movements) {
       this.movements = movements;
        this.pos = pos;
        this.g = g;
        this.goal = goal;
        this.parent = null;
        this.h = pos.manhattanDistanceTo(goal);
    }

    public Node(Position pos, double g, Position goal, Node parent) {
        this.pos = pos;
        this.g = g;
        this.goal = goal;
        this.parent = parent;
        this.h = pos.manhattanDistanceTo(goal);
        this.movements = parent.movements;
    }

    public Boolean isGoal() {
        return pos.equals(goal);
    }

    public double g() {
        return g;
    }

    public double h() {
        return h;
    }

    public double f() {
        return this.g() + this.h();
    }

    public Position getPosition() {
        return pos;
    }

    private double calculateCost(Position oldPos, Position newPos) {
        return Math.sqrt(Math.abs(oldPos.i - newPos.i) + Math.abs(oldPos.j - newPos.j));
    }

    public List<Node> generateNeighbors(Maze maze) {
        List<Node> result = new ArrayList<>(movements.length);
        for(MazeMovement m : movements) {
            int i = this.getPosition().i + m.incX();
            int j = this.getPosition().j + m.incY();
            Cell cell = maze.get(i, j);
            if(cell != Cell.OBSTACLE && cell != Cell.INITIAL) {
                Position pos = new Position(i, j);
                double cost = calculateCost(this.pos, pos);
                Node node = new Node(pos, this.g() + cost, goal, this);
                result.add(node);
            }
        }
        return result;
    }

    public List<Node> toList() {
        List<Node> res = new ArrayList<>();

        Node current = this;
        while (current != null) {
            res.add(current);
            current = current.parent;
        }
        return res;
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(f(), node.f());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return pos.equals(node.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }
}
