package ru.java.bondarmax.graph_main;

import ru.java.bondarmax.graph.Graph;

public class Main {
    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
              // 0  1  2  3  4
                {0, 1, 1, 0, 1},
                {1, 0, 1, 1, 0},
                {1, 1, 0, 1, 1},
                {0, 1, 1, 0, 1},
                {1, 0, 1, 1, 0}
        };

        Graph graph = new Graph(adjacencyMatrix);

        System.out.println("Матрица смежности:");

        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < graph.getVertexCount(); j++) {
                System.out.print(graph.getAdjacencyMatrix()[i][j] + " ");
            }

            System.out.println();
        }

        String lineSeparator = System.lineSeparator();

        System.out.println(lineSeparator + "=== Рекурсивный поиск в глубину ===");
        int[] componentsDFSRecursive = graph.performDeepSearchRecursive();
        Graph.printComponents(componentsDFSRecursive);

        System.out.println(lineSeparator + "=== Итеративный поиск в глубину ===");
        int[] componentsDFSIterative = graph.performDeepSearchIterative();
        Graph.printComponents(componentsDFSIterative);

        System.out.println(lineSeparator + "=== Поиск в ширину ===");
        int[] componentsBFS = graph.performWideSearch();
        Graph.printComponents(componentsBFS);
    }
}
