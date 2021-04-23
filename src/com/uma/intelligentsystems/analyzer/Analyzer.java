package com.uma.intelligentsystems.analyzer;

import com.uma.intelligentsystems.AStar;
import com.uma.intelligentsystems.Maze;
import com.uma.intelligentsystems.MazeMovement;
import com.uma.intelligentsystems.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Analyzer {

    private final Random rand;
    private final int rows;
    private final int columns;
    private final DecimalFormat df = new DecimalFormat("#.#");

    public Analyzer(Random rand, int rows, int columns) {
        this.rand = rand;
        this.rows = rows;
        this.columns = columns;
    }

    public void run(String fileNameLengths, String fileNameFailures, int times, MazeMovement[] movements) {
        Map<Float, Integer> failures = new TreeMap<>();
        Map<Float, Collection<Integer>> lengths = new TreeMap<>();

        for (int i = 0; i < 10; i++) {
            float obstacles = i * 0.1f;
            failures.putIfAbsent(obstacles, 0);
            lengths.putIfAbsent(obstacles, new ArrayList<>());

            for (int j = 0; j < times; j++) {
                Maze maze = new Maze(rows, columns, obstacles, rand);
                AStar as = new AStar(maze, movements);
                Collection<Node> result = as.solve();

                if (result == null) {
                    Integer obs = failures.get(obstacles);
                    failures.put(obstacles, obs + 1);
                } else {
                    int len = result.size();
                    Collection<Integer> col = lengths.get(obstacles);
                    col.add(len);
                }
            }
        }

        Collection<Float> cols = lengths.values().stream().map(this::mean).collect(Collectors.toSet());
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

    private float mean(Collection<Integer> collection) {
        int total = 0;

        for (int l : collection) {
            total += l;
        }

        return total == 0 ? 0 : total / (float) collection.size();
    }


}
