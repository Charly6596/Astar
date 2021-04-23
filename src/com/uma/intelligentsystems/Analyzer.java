package com.uma.intelligentsystems.analyzer;

import com.uma.intelligentsystems.*;
import com.uma.intelligentsystems.maze.Cell;
import com.uma.intelligentsystems.maze.Maze;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Analyzer {

    private final Random rand;
    private final int rows;
    private final int columns;
    private final DecimalFormat df = new DecimalFormat("#.#####");

    public Analyzer(Random rand, int rows, int columns) {
        this.rand = rand;
        this.rows = rows;
        this.columns = columns;
    }

    public void run(String fileNameLengths, String fileNameFailures, int times, MazeMovement[] movements) {
        Map<Integer, Integer> failures = new TreeMap<>();
        Map<Integer, Collection<Double>> lengths = new TreeMap<>();

        for (int i = 0; i < 100; i += 10) {
            float obstacles = i * 0.01f;
            failures.putIfAbsent(i, 0);
            lengths.putIfAbsent(i, new ArrayList<>());

            for (int j = 0; j < times; j++) {
                Maze maze = new Maze(rows, columns, obstacles, rand);
                AStar as = new AStar(maze, movements);
                Collection<Node> result = as.solve();

                if (result == null) {
                    Integer obs = failures.get(i);
                    failures.put(i, obs + 1);
                    Collection<Double> col = lengths.get(i);
                    col.add(0d);
                } else {
                    double len = result.stream().findFirst().get().g();
                    Collection<Double> col = lengths.get(i);
                    col.add(len);
                }
            }
        }

        List<Float> cols = lengths.values().stream().map(this::mean).collect(Collectors.toList());
        save(fileNameLengths, lengths.keySet(), cols);
        save(fileNameFailures, failures.keySet(), failures.values());
    }

    private void save(String fileName, Collection<? extends Number> header, Collection<? extends Number> result) {
        try {
            FileWriter file = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(file);

            printLine(pw, header);
            printLine(pw, result);

            pw.close();
            file.close();
        } catch (IOException e) {
            System.out.println("Error opening file " + fileName + " while saving lengths");
        }
    }

    private void printLine(PrintWriter pw, Collection<? extends Number> keys) {
        String res = keys.stream().map(df::format).collect(Collectors.joining(","));
        pw.println(res);
    }

    private float mean(Collection<? extends Number> collection) {
        double total = 0;

        for (Number l : collection) {
            total += l.doubleValue();
        }

        if(total == 0) {
            return 0;
        }
        BigDecimal res = new BigDecimal(total / collection.size());
        res.setScale(5, RoundingMode.HALF_UP);

        return res.floatValue();
    }

    public void compareMovements(MazeMovement[] movements1, MazeMovement[] movements2) {
        String name1 = movements1[0].getName();
        String name2 = movements2[0].getName();
        Maze m = new Maze(rows, columns, 0.3, rand);
        AStar aStar = new AStar(m, movements1);
        var res1 = aStar.solve();

        AStar aStar2 = new AStar(m, movements2);
        var res2 = aStar2.solve();

        m.applyPath(res1, Cell.OPTIMAL_ALTERNATIVE1);
        m.applyPath(res2, Cell.OPTIMAL_ALTERNATIVE2);

        if(res1 == null) {
            System.out.printf("Could not find a valid path for %s\n", name1);
        } else {
            System.out.printf("%s length: %f\n", name1, res1.stream().findFirst().get().g());
        }

        if(res2 == null) {
            System.out.printf("Could not find a valid path for %s\n", name2);
        } else {
            System.out.printf("%s length: %f\n", name2, res2.stream().findFirst().get().g());
        }

        System.out.println(m);
    }
}
