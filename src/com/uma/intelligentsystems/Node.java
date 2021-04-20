package com.uma.intelligentsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
    private final Position goal;
    private Position pos;
    private int g;
    private int h;
    private Node parent;
    private MazeMovement[] movements;

    public Node(Position pos, int g, Position goal, MazeMovement[] movements) {
       this.movements = movements;
        this.pos = pos;
        this.g = g;
        this.goal = goal;
        this.parent = null;
        this.h = pos.manhattanDistanceTo(goal);
    }

    public Node(Position pos, int g, Position goal, Node parent) {
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

    public Node getParent() {
        return parent;
    }

    public int g() {
        return g;
    }

    public int h() {
        return h;
    }

    public int f() {
        return this.g() + this.h();
    }

    public Position getPosition() {
        return pos;
    }

    public List<Node> generateNeighbors(Maze maze) {
        List<Node> result = new ArrayList<>(movements.length);
        for(MazeMovement m : movements) {
            int i = this.getPosition().i + m.incX();
            int j = this.getPosition().j + m.incY();
            Cell cell = maze.get(i, j);
            if(cell == Cell.EMPTY || cell == Cell.GOAL) {
                Position pos = new Position(i, j);
                Node node = new Node(pos, this.g() + 1, goal, this);
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
        return Integer.compare(f(), node.f());
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
