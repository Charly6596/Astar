package com.uma.intelligentsystems;

import com.uma.intelligentsystems.analyzer.Analyzer;

import java.util.*;

public class Main {

    final static int ROWS = 60;
    final static int COLM = 80;

    final static int TIMES = 50;

    public static void main(String[] args) {
        Random rand = new Random(100);
        Analyzer analyzer = new Analyzer(rand, ROWS, COLM);
        analyzer.run("lengths_von_neumann.csv", "failures_von_neumann.csv", TIMES, VonNeumanMove.values());
        rand.setSeed(100);
        analyzer.run("lengths_moore.csv","failures_moore.csv", TIMES, MooreMove.values());
    }


}
