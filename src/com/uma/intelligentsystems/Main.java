package com.uma.intelligentsystems;

import com.uma.intelligentsystems.astar.MooreMove;
import com.uma.intelligentsystems.astar.VonNeumannMove;

import java.util.*;

public class Main {

    final static int ROWS = 60;
    final static int COLM = 80;

    final static int TIMES = 10000;
    public static void main(String[] args) {
        Random rand = new Random(7);

        Analyzer analyzer = new Analyzer(rand, ROWS, COLM);
        analyzer.compareMovements(VonNeumannMove.values(), MooreMove.values());

        rand.setSeed(100);
        analyzer.run("lengths_von_neumann.csv", "failures_von_neumann.csv", TIMES, VonNeumannMove.values());
        rand.setSeed(100);
        analyzer.run("lengths_moore.csv","failures_moore.csv", TIMES, MooreMove.values());
    }
}
