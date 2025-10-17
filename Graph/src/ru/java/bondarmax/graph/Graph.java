package ru.java.bondarmax.graph;

import java.util.*;

/**
 * Класс для реализации алгоритмов обхода графа (в глубину и в ширину)
 * Граф задается матрицей смежности
 */
public class Graph {
    private int[][] adjacencyMatrix; // матрица смежности графа
    private int vertexCount;         // количество вершин в графе

    /**
     * Конструктор класса Graph
     * @param matrix матрица смежности графа
     */
    public Graph(int[][] matrix) {
        this.adjacencyMatrix = matrix;
        this.vertexCount = matrix.length;
    }

    /**
     * Получить матрицу смежности графа
     * @return матрица смежности
     */
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    /**
     * Установить новую матрицу смежности графа
     * @param adjacencyMatrix новая матрица смежности
     */
    public void setAdjacencyMatrix(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.vertexCount = adjacencyMatrix.length;
    }

    /**
     * Получить количество вершин в графе
     * @return количество вершин
     */
    public int getVertexCount() {
        return vertexCount;
    }


    /**
     * Обход графа в глубину с использованием рекурсии
     * @return массив, где для каждой вершины указан номер её компоненты связности
     */
    public int[] depthFirstSearchRecursive() {
        boolean[] visited = new boolean[vertexCount];
        int[] componentIds = new int[vertexCount];
        Arrays.fill(componentIds, -1);
        int currentComponentId = 0;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!visited[vertex]) {
                exploreComponentDFSRecursive(vertex, visited, componentIds, currentComponentId);
                currentComponentId++;
            }
        }

        return componentIds;
    }

    /**
     * Вспомогательный метод для рекурсивного обхода в глубину
     * @param currentVertex текущая вершина для обработки
     * @param visited массив посещенных вершин
     * @param componentIds массив для хранения номеров компонент
     * @param componentId номер текущей компоненты связности
     */
    private void exploreComponentDFSRecursive(int currentVertex, boolean[] visited, int[] componentIds, int componentId) {
        visited[currentVertex] = true;
        componentIds[currentVertex] = componentId;

        // Рекурсивно посещаем всех непосещенных соседей
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                exploreComponentDFSRecursive(neighbor, visited, componentIds, componentId);
            }
        }
    }

    /**
     * Обход графа в глубину без использования рекурсии.
     * Использует массив для эмуляции стека
     * @return массив, где для каждой вершины указан номер её компоненты связности
     */
    public int[] depthFirstSearchIterative() {
        boolean[] visited = new boolean[vertexCount];
        int[] componentIds = new int[vertexCount];
        Arrays.fill(componentIds, -1);
        int currentComponentId = 0;

        for (int startVertex = 0; startVertex < vertexCount; startVertex++) {
            if (!visited[startVertex]) {
                exploreComponentDFSIterative(startVertex, visited, componentIds, currentComponentId);
                currentComponentId++;
            }
        }
        return componentIds;
    }

    /**
     * Вспомогательный метод для итеративного обхода в глубину
     * @param startVertex начальная вершина компоненты
     * @param visited массив посещенных вершин
     * @param componentIds массив для хранения номеров компонент
     * @param componentId номер текущей компоненты связности
     */
    private void exploreComponentDFSIterative(int startVertex, boolean[] visited, int[] componentIds, int componentId) {
        int[] stack = new int[vertexCount];
        int stackSize = 0;

        // Добавляем начальную вершину в стек
        stack[stackSize++] = startVertex;
        visited[startVertex] = true;
        componentIds[startVertex] = componentId;

        while (stackSize > 0) {
            // Извлекаем вершину из стека
            int currentVertex = stack[--stackSize];

            // Добавляем всех непосещенных соседей в стек
            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                    stack[stackSize++] = neighbor;
                    visited[neighbor] = true;
                    componentIds[neighbor] = componentId;
                }
            }
        }
    }

    /**
     * Обход графа в ширину (Breadth-First Search)
     * Использует массив для эмуляции очереди
     * @return массив, где для каждой вершины указан номер её компоненты связности
     */
    public int[] breadthFirstSearch() {
        boolean[] visited = new boolean[vertexCount];
        int[] componentIds = new int[vertexCount];
        Arrays.fill(componentIds, -1);
        int currentComponentId = 0;

        for (int startVertex = 0; startVertex < vertexCount; startVertex++) {
            if (!visited[startVertex]) {
                exploreComponentBFS(startVertex, visited, componentIds, currentComponentId);
                currentComponentId++;
            }
        }
        return componentIds;
    }

    /**
     * Вспомогательный метод для обхода в ширину
     * @param startVertex начальная вершина компоненты
     * @param visited массив посещенных вершин
     * @param componentIds массив для хранения номеров компонент
     * @param componentId номер текущей компоненты связности
     */
    private void exploreComponentBFS(int startVertex, boolean[] visited, int[] componentIds, int componentId) {
        // Эмулируем очередь с помощью массива
        int[] queue = new int[vertexCount];
        int queueFront = 0; // указатель на начало очереди
        int queueRear = 0;  // указатель на конец очереди

        // Добавляем начальную вершину в очередь
        queue[queueRear] = startVertex;
        queueRear++;
        visited[startVertex] = true;
        componentIds[startVertex] = componentId;

        while (queueFront < queueRear) {
            // Извлекаем вершину из начала очереди
            int currentVertex = queue[queueFront++];

            // Добавляем всех непосещенных соседей в конец очереди
            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                    queue[queueRear++] = neighbor;
                    visited[neighbor] = true;
                    componentIds[neighbor] = componentId;
                }
            }
        }
    }

    /**
     * Вспомогательный метод для вывода компонент связности
     * @param componentIds массив с номерами компонент для каждой вершины
     */
    public static void printComponents(int[] componentIds) {
        int maxComponentId = 0;
        for (int componentId : componentIds) {
            if (componentId > maxComponentId) {
                maxComponentId = componentId;
            }
        }

        for (int componentId = 0; componentId <= maxComponentId; componentId++) {
            System.out.print("Компонента " + (componentId + 1) + ": ");
            for (int vertex = 0; vertex < componentIds.length; vertex++) {
                if (componentIds[vertex] == componentId) {
                    System.out.print(vertex + " ");
                }
            }
            System.out.println();
        }
    }
}