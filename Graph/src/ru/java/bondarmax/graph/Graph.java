package ru.java.bondarmax.graph;

import java.util.*;
import java.util.function.IntConsumer;

/**
 * Класс для реализации алгоритмов обхода графа (в глубину и в ширину)
 * Граф задается матрицей смежности
 */
public class Graph {
    private final int[][] adjacencyMatrix; // матрица смежности графа
    private final int verticesCount;         // количество вершин в графе

    /**
     * Конструктор класса Graph
     *
     * @param adjacencyMatrix матрица смежности графа
     */
    public Graph(int[][] adjacencyMatrix) {
        if (adjacencyMatrix == null) {
            throw new IllegalArgumentException("Матрица смежности не может быть null");
        }

        // Проверяем, что матрица квадратная
        for (int[] matrix : adjacencyMatrix) {
            if (matrix == null || matrix.length != adjacencyMatrix.length) {
                throw new IllegalArgumentException("Матрица смежности должна быть квадратной");
            }
        }

        this.adjacencyMatrix = adjacencyMatrix;
        this.verticesCount = adjacencyMatrix.length;
    }

    /**
     * Выполняет обход графа в глубину с использованием рекурсии.
     * Алгоритм посещает все вершины графа, начиная с вершины 0 и продолжая по всем компонентам связности.
     * Для каждой посещенной вершины вызывается метод accept переданного потребителя.
     * Порядок обхода: сначала обрабатывается текущая вершина, затем рекурсивно все её непосещенные соседи.
     *
     * @param visitor потребитель для обработки вершин, вызывается для каждой посещаемой вершины
     * @throws IllegalArgumentException если visitor равен null
     */
    public void traverseDfsRecursive(IntConsumer visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("Потребитель не может быть null");
        }
        boolean[] visited = new boolean[verticesCount];

        for (int vertexIndex = 0; vertexIndex < verticesCount; vertexIndex++) {
            if (!visited[vertexIndex]) {
                traverseDfsRecursive(vertexIndex, visited, visitor);
            }
        }
    }

    /**
     * Выполняет обход графа в глубину без использования рекурсии.
     * Использует стек для эмуляции рекурсивного вызова.
     * Алгоритм посещает все вершины графа, обрабатывая каждую компоненту связности.
     * Для каждой посещенной вершины вызывается метод accept переданного потребителя.
     * Порядок обхода: вершины обрабатываются в порядке LIFO (последний пришел - первый вышел).
     *
     * @param visitor потребитель для обработки вершин, вызывается для каждой посещаемой вершины
     * @throws IllegalArgumentException если visitor равен null
     */
    public void traverseDfsIterative(IntConsumer visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("Потребитель не может быть null");
        }
        boolean[] visited = new boolean[verticesCount];

        for (int startVertexIndex = 0; startVertexIndex < verticesCount; startVertexIndex++) {
            if (!visited[startVertexIndex]) {
                traverseDfsIterative(startVertexIndex, visited, visitor);
            }
        }
    }

    /**
     * Выполняет обход графа в ширину.
     * Использует очередь для обработки вершин по уровням.
     * Алгоритм посещает все вершины графа, начиная с ближайших соседей.
     * Для каждой посещенной вершины вызывается метод accept переданного потребителя.
     * Порядок обхода: вершины обрабатываются в порядке FIFO (первый пришел - первый вышел).
     *
     * @param visitor потребитель для обработки вершин, вызывается для каждой посещаемой вершины
     * @throws IllegalArgumentException если visitor равен null
     */
    public void traverseBfs(IntConsumer visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("Потребитель не может быть null");
        }
        boolean[] visited = new boolean[verticesCount];

        for (int startVertexIndex = 0; startVertexIndex < verticesCount; startVertexIndex++) {
            if (!visited[startVertexIndex]) {
                traverseBfs(startVertexIndex, visited, visitor);
            }
        }
    }

    /**
     * Вспомогательный метод для рекурсивного обхода в глубину.
     * Помечает текущую вершину как посещенную, вызывает потребитель и рекурсивно обрабатывает всех непосещенных соседей.
     *
     * @param currentVertexIndex текущая обрабатываемая вершина
     * @param visited            массив отметок о посещении вершин
     * @param visitor            потребитель для обработки вершин
     */
    private void traverseDfsRecursive(int currentVertexIndex, boolean[] visited, IntConsumer visitor) {
        visited[currentVertexIndex] = true;
        visitor.accept(currentVertexIndex);

        for (int neighborIndex = 0; neighborIndex < verticesCount; neighborIndex++) {
            if (adjacencyMatrix[currentVertexIndex][neighborIndex] != 0 && !visited[neighborIndex]) {
                traverseDfsRecursive(neighborIndex, visited, visitor);
            }
        }
    }

    /**
     * Вспомогательный метод для итеративного обхода в глубину.
     * Использует стек для хранения вершин, подлежащих обработке.
     * На каждой итерации извлекает вершину из стека, вызывает потребитель и добавляет всех непосещенных соседей в стек.
     *
     * @param startVertexIndex начальная вершина для обхода компоненты связности
     * @param visited          массив отметок о посещении вершин
     * @param visitor          потребитель для обработки вершин
     */
    private void traverseDfsIterative(int startVertexIndex, boolean[] visited, IntConsumer visitor) {
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(startVertexIndex);

        while (!stack.isEmpty()) {
            int currentVertexIndex = stack.pop();

            if (!visited[currentVertexIndex]) {
                visited[currentVertexIndex] = true;
                visitor.accept(currentVertexIndex);

                // Добавляем всех непосещенных соседей в стек
                for (int neighborIndex = verticesCount - 1; neighborIndex >= 0; neighborIndex--) {
                    if (adjacencyMatrix[currentVertexIndex][neighborIndex] != 0 && !visited[neighborIndex]) {
                        stack.push(neighborIndex);
                    }
                }
            }
        }
    }

    /**
     * Вспомогательный метод для обхода в ширину.
     * Использует очередь для обработки вершин по уровням.
     * На каждой итерации извлекает вершину из начала очереди, вызывает потребитель и добавляет всех непосещенных соседей в конец очереди.
     *
     * @param startVertexIndex начальная вершина для обхода компоненты связности
     * @param visited          массив отметок о посещении вершин
     * @param visitor          потребитель для обработки вершин
     */
    private void traverseBfs(int startVertexIndex, boolean[] visited, IntConsumer visitor) {
        Queue<Integer> queue = new ArrayDeque<>();

        queue.offer(startVertexIndex);
        visited[startVertexIndex] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.remove();
            visitor.accept(currentVertex);

            for (int neighborIndex = 0; neighborIndex < verticesCount; neighborIndex++) {
                if (adjacencyMatrix[currentVertex][neighborIndex] != 0 && !visited[neighborIndex]) {
                    queue.offer(neighborIndex);
                    visited[neighborIndex] = true;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        sb.append("Список смежности графа:").append(lineSeparator);

        for (int vertexIndex = 0; vertexIndex < verticesCount; vertexIndex++) {
            sb.append(vertexIndex).append(" -> ");

            boolean hasNeighbors = false;

            for (int neighborIndex = 0; neighborIndex < verticesCount; neighborIndex++) {
                if (adjacencyMatrix[vertexIndex][neighborIndex] != 0) {
                    if (hasNeighbors) {
                        sb.append(", ");
                    }

                    sb.append(neighborIndex);
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