package ru.java.bondarmax.graph;

import java.util.*;

/**
 * Класс для реализации алгоритмов обхода графа (в глубину и в ширину)
 * Граф задается матрицей смежности
 */
public class Graph {
    private final int[][] adjacencyMatrix; // матрица смежности графа
    private final int vertexCount;         // количество вершин в графе

    /**
     * Конструктор класса Graph
     *
     * @param matrix матрица смежности графа
     */
    public Graph(int[][] matrix) {
        this.adjacencyMatrix = matrix;
        this.vertexCount = matrix.length;
    }

    /**
     * Получить матрицу смежности графа
     *
     * @return матрица смежности
     */
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    /**
     * Получить количество вершин в графе
     *
     * @return количество вершин
     */
    public int getVertexCount() {
        return vertexCount;
    }


    /**
     * Обход графа в глубину с использованием рекурсии
     *
     * @return массив, где для каждой вершины указан номер её компоненты связности
     */
    public int[] performDeepSearchRecursive() {
        boolean[] visited = new boolean[vertexCount];
        int[] componentIds = new int[vertexCount];
        Arrays.fill(componentIds, -1);
        int currentComponentId = 0;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!visited[vertex]) {
                exploreComponentDeepRecursive(vertex, visited, componentIds, currentComponentId);
                currentComponentId++;
            }
        }

        return componentIds;
    }

    /**
     * Вспомогательный метод для рекурсивного обхода в глубину
     *
     * @param currentVertex текущая вершина для обработки
     * @param visited       массив посещенных вершин
     * @param componentIds  массив для хранения номеров компонент
     * @param componentId   номер текущей компоненты связности
     */
    private void exploreComponentDeepRecursive(int currentVertex, boolean[] visited, int[] componentIds, int componentId) {
        visited[currentVertex] = true;
        componentIds[currentVertex] = componentId;

        // Рекурсивно посещаем всех непосещенных соседей
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                exploreComponentDeepRecursive(neighbor, visited, componentIds, componentId);
            }
        }
    }

    /**
     * Обход графа в глубину без использования рекурсии.
     * Использует массив для эмуляции стека
     *
     * @return массив, где для каждой вершины указан номер её компоненты связности
     */
    public int[] performDeepSearchIterative() {
        boolean[] visited = new boolean[vertexCount];
        int[] componentIds = new int[vertexCount];
        Arrays.fill(componentIds, -1);
        int currentComponentId = 0;

        for (int startVertex = 0; startVertex < vertexCount; startVertex++) {
            if (!visited[startVertex]) {
                exploreComponentDeepIterative(startVertex, visited, componentIds, currentComponentId);
                currentComponentId++;
            }
        }

        return componentIds;
    }

    /**
     * Вспомогательный метод для итеративного обхода в глубину
     *
     * @param startVertex  начальная вершина компоненты
     * @param visited      массив посещенных вершин
     * @param componentIds массив для хранения номеров компонент
     * @param componentId  номер текущей компоненты связности
     */
    private void exploreComponentDeepIterative(int startVertex, boolean[] visited, int[] componentIds, int componentId) {
        int[] stack = new int[vertexCount];
        int stackSize = 0;

        // Добавляем начальную вершину в стек
        stack[stackSize] = startVertex;
        stackSize++;
        visited[startVertex] = true;
        componentIds[startVertex] = componentId;

        while (stackSize > 0) {
            // Извлекаем вершину из стека (исправлено!)
            stackSize--;
            int currentVertex = stack[stackSize];

            // Добавляем всех непосещенных соседей в стек
            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                    stack[stackSize] = neighbor;
                    stackSize++;
                    visited[neighbor] = true;
                    componentIds[neighbor] = componentId;
                }
            }
        }
    }

    /**
     * Обход графа в ширину.
     * Использует массив для эмуляции очереди
     *
     * @return массив, где для каждой вершины указан номер её компоненты связности
     */
    public int[] performWideSearch() {
        boolean[] visited = new boolean[vertexCount];
        int[] componentIds = new int[vertexCount];
        Arrays.fill(componentIds, -1);
        int currentComponentId = 0;

        for (int startVertex = 0; startVertex < vertexCount; startVertex++) {
            if (!visited[startVertex]) {
                exploreComponentWide(startVertex, visited, componentIds, currentComponentId);
                currentComponentId++;
            }
        }

        return componentIds;
    }

    /**
     * Вспомогательный метод для обхода в ширину
     *
     * @param startVertex  начальная вершина компоненты
     * @param visited      массив посещенных вершин
     * @param componentIds массив для хранения номеров компонент
     * @param componentId  номер текущей компоненты связности
     */
    private void exploreComponentWide(int startVertex, boolean[] visited, int[] componentIds, int componentId) {
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
                    queue[queueRear] = neighbor;
                    queueRear++;
                    visited[neighbor] = true;
                    componentIds[neighbor] = componentId;
                }
            }
        }
    }

    /**
     * Вспомогательный метод для вывода компонент связности
     *
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        sb.append("Список смежности графа:").append(lineSeparator);

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            sb.append(vertex).append(" -> ");

            boolean hasNeighbors = false;

            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                if (adjacencyMatrix[vertex][neighbor] != 0) {
                    if (hasNeighbors) {
                        sb.append(", ");
                    }

                    sb.append(neighbor);
                    hasNeighbors = true;
                }
            }

            if (!hasNeighbors) {
                sb.append("нет соседей");
            }

            sb.append(lineSeparator);
        }

        return sb.toString();
    }
}