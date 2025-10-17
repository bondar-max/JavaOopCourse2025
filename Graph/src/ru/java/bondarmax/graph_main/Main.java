package ru.java.bondarmax.graph_main;

import ru.java.bondarmax.graph.Graph;

public class Main {
    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
                // 0  1  2  3  4  5
                { 0, 1, 0, 0, 0, 0 }, // вершина 0 связана с вершиной 1
                { 1, 0, 1, 0, 0, 0 }, // вершина 1 связана с вершинами 0 и 2
                { 0, 1, 0, 0, 0, 0 }, // вершина 2 связана с вершиной 1
                { 0, 0, 0, 0, 1, 0 }, // вершина 3 связана с вершиной 4
                { 0, 0, 0, 1, 0, 0 }, // вершина 4 связана с вершиной 3
                { 0, 0, 0, 0, 0, 0 }  // вершина 5 не связана ни с кем
        };

        Graph graph = new Graph(adjacencyMatrix);

        // Демонстрация всех трех алгоритмов обхода
        System.out.println("Обход в глубину (рекурсия):");
        int[] dfsRecursiveComponents = graph.depthFirstSearchRecursive();
        Graph.printComponents(dfsRecursiveComponents);
        System.out.println();

        System.out.println("Обход в глубину (без рекурсии):");
        int[] dfsIterativeComponents = graph.depthFirstSearchIterative();
        Graph.printComponents(dfsIterativeComponents);
        System.out.println();

        System.out.println("Обход в ширину:");
        int[] bfsComponents = graph.breadthFirstSearch();
        Graph.printComponents(bfsComponents);
    }
}
