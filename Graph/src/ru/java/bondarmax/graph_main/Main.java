package ru.java.bondarmax.graph_main;

import ru.java.bondarmax.graph.Graph;

public class Main {
    public static void main(String[] args) {
        Graph graph = getGraph();

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
