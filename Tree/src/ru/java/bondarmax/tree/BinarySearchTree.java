package ru.java.bondarmax.tree;

import java.util.*;

// Класс бинарного дерева поиска
public class BinarySearchTree<T extends Comparable<T>> {
    private TreeNode<T> root;
    private int size;

    /**
     * Вставляет новый элемент в бинарное дерево поиска.
     * Если элемент уже существует, дерево не изменяется.
     *
     * @param data элемент для вставки
     * @throws IllegalArgumentException если передан null
     */
    @SuppressWarnings("DataFlowIssue")
    public boolean insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Нельзя вставлять null значения");
        }

        int oldSize = size;
        root = insertRecursive(root, data);
        return size > oldSize;
    }

    /**
     * Рекурсивный вспомогательный метод для вставки элемента.
     *
     * @param node текущий узел для сравнения
     * @param data элемент для вставки
     * @return новый корень поддерева после вставки
     */
    private TreeNode<T> insertRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            size++;
            return new TreeNode<>(data);
        }

        if (data.compareTo(node.data) < 0) {
            node.left = insertRecursive(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insertRecursive(node.right, data);
        }
        // Если элемент уже существует, ничего не делаем

        return node;
    }

    /**
     * Проверяет, содержится ли элемент в дереве.
     *
     * @param data элемент для поиска
     * @return true если элемент найден, false в противном случае
     * @throws IllegalArgumentException если передан null
     */
    @SuppressWarnings("DataFlowIssue")
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Нельзя искать null значения");
        }

        return containsRecursively(root, data);
    }

    /**
     * Рекурсивный вспомогательный метод для поиска элемента.
     *
     * @param node текущий узел для проверки
     * @param data искомый элемент
     * @return true если элемент найден в поддереве
     */
    private boolean containsRecursively(TreeNode<T> node, T data) {
        if (node == null) {
            return false;
        }

        if (data.compareTo(node.data) == 0) {
            return true;
        } else if (data.compareTo(node.data) < 0) {
            return containsRecursively(node.left, data);
        } else {
            return containsRecursively(node.right, data);
        }
    }

    /**
     * Удаляет первое вхождение элемента из дерева.
     *
     * @param data элемент для удаления
     * @return true если элемент был найден и удален, false если элемент не найден
     * @throws IllegalArgumentException если передан null
     */
    @SuppressWarnings("DataFlowIssue")
    public boolean remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Нельзя удалять null значения");
        }

        int initialSize = size;
        root = removeRecursively(root, data);
        return size != initialSize;
    }

    /**
     * Рекурсивный вспомогательный метод для удаления элемента.
     * Обрабатывает три случая: узел без детей, с одним ребенком и с двумя детьми.
     *
     * @param node текущий узел для проверки
     * @param data элемент для удаления
     * @return новый корень поддерева после удаления
     */
    private TreeNode<T> removeRecursively(TreeNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.data) < 0) {
            node.left = removeRecursively(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = removeRecursively(node.right, data);
        } else {
            // Узел найден
            size--;

            // Случай 1: узел без детей или с одним ребенком
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Случай 2: узел с двумя детьми.
            // Находим минимальный элемент в правом поддереве (преемника)
            node.data = findMinimumNode(node.right).data;
            // Удаляем минимальный элемент из правого поддерева
            node.right = removeRecursively(node.right, node.data);
            size++; // компенсируем уменьшение размера при рекурсивном удалении
        }

        return node;
    }

    /**
     * Находит узел с минимальным значением в поддереве.
     *
     * @param node корень поддерева для поиска
     * @return узел с минимальным значением в поддереве
     */
    private TreeNode<T> findMinimumNode(TreeNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    /**
     * Возвращает количество элементов в дереве.
     *
     * @return число элементов в дереве
     */
    public int getSize() {
        return size;
    }

    /**
     * Проверяет, является ли дерево пустым.
     *
     * @return true если дерево не содержит элементов, false в противном случае
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Выполняет обход дерева в ширину.
     * Посещает узлы уровень за уровнем, начиная с корня.
     *
     * @return список элементов в порядке обхода в ширину
     */
    public List<T> traverseByLevelOrder() {
        List<T> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            result.add(current.data);

            if (current.left != null) {
                queue.offer(current.left);
            }

            if (current.right != null) {
                queue.offer(current.right);
            }
        }

        return result;
    }

    /**
     * Выполняет обход дерева в глубину с использованием рекурсии.
     * Использует прямой порядок: корень → левое поддерево → правое поддерево.
     *
     * @return список элементов в порядке прямого обхода
     */
    public List<T> traversePreOrderWithRecursion() {
        List<T> result = new ArrayList<>();
        traversePreOrderRecursively(root, result);
        return result;
    }

    /**
     * Рекурсивный вспомогательный метод для обхода в глубину.
     *
     * @param node   текущий обрабатываемый узел
     * @param result список для сохранения результатов обхода
     */
    private void traversePreOrderRecursively(TreeNode<T> node, List<T> result) {
        if (node != null) {
            // Прямой обход: корень -> левый -> правый
            result.add(node.data);
            traversePreOrderRecursively(node.left, result);
            traversePreOrderRecursively(node.right, result);
        }
    }

    /**
     * Выполняет обход дерева в глубину без использования рекурсии.
     * Использует стек для имитации рекурсии. Прямой порядок.
     *
     * @return список элементов в порядке прямого обхода.
     */
    public List<T> traversePreOrderWithIteration() {
        List<T> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<T> current = stack.pop();
            result.add(current.data);

            // Сначала добавляем правого ребенка, потом левого
            // чтобы левый обрабатывался первым (LIFO)
            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return result;
    }
}