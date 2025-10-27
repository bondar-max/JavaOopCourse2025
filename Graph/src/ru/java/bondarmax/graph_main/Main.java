package ru.java.bondarmax.graph_main;

import ru.java.bondarmax.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = getGraph();

        String lineSeparator = System.lineSeparator();

        System.out.println(lineSeparator + "=== Рекурсивный поиск в глубину ===");
        List<Integer> dfsRecursiveResult = new ArrayList<>();
        graph.traverseDfsRecursive(dfsRecursiveResult::add);
        System.out.println("Порядок обхода: " + dfsRecursiveResult);

        System.out.println(lineSeparator + "=== Итеративный поиск в глубину ===");
        List<Integer> dfsIterativeResult = new ArrayList<>();
        graph.traverseDfsIterative(dfsIterativeResult::add);
        System.out.println("Порядок обхода: " + dfsIterativeResult);

        System.out.println(lineSeparator + "=== Поиск в ширину ===");
        List<Integer> bfsResult = new ArrayList<>();
        graph.traverseBfs(bfsResult::add);
        System.out.println("Порядок обхода: " + bfsResult);

        System.out.println();

        System.out.println(graph);
    }

    private static Graph getGraph() {
        int[][] adjacencyMatrix = {
              // 0  1  2  3  4  5
                {0, 1, 1, 0, 0, 0}, // Вершина 0 связана с 1, 2
                {1, 0, 1, 0, 0, 0}, // Вершина 1 связана с 0, 2
                {1, 1, 0, 0, 0, 0}, // Вершина 2 связана с 0, 1
                {0, 0, 0, 0, 1, 1}, // Вершина 3 связана с 4, 5
                {0, 0, 0, 1, 0, 1}, // Вершина 4 связана с 3, 5
                {0, 0, 0, 1, 1, 0}  // Вершина 5 связана с 3, 4
        };

        return new Graph(adjacencyMatrix);
    }
}