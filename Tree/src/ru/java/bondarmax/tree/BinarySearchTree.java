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
    public void insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Нельзя вставлять null значения");
        }

        root = insertRecursive(root, data);
        size++;
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

        return containsRecursive(root, data);
    }

    /**
     * Рекурсивный вспомогательный метод для поиска элемента.
     *
     * @param node текущий узел для проверки
     * @param data искомый элемент
     * @return true если элемент найден в поддереве
     */
    private boolean containsRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            return false;
        }

        if (data.compareTo(node.data) == 0) {
            return true;
        } else if (data.compareTo(node.data) < 0) {
            return containsRecursive(node.left, data);
        } else {
            return containsRecursive(node.right, data);
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
        root = removeRecursive(root, data);
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
    private TreeNode<T> removeRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.data) < 0) {
            node.left = removeRecursive(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = removeRecursive(node.right, data);
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
            // Находим минимальный элемент в правом поддереве (преемника in-order)
            node.data = findMin(node.right).data;
            // Удаляем минимальный элемент из правого поддерева
            node.right = removeRecursive(node.right, node.data);
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
    private TreeNode<T> findMin(TreeNode<T> node) {
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
    public List<T> breadthFirstTraversal() {
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
    public List<T> depthFirstTraversalRecursive() {
        List<T> result = new ArrayList<>();
        depthFirstRecursive(root, result);
        return result;
    }

    /**
     * Рекурсивный вспомогательный метод для обхода в глубину.
     *
     * @param node текущий обрабатываемый узел
     * @param result список для сохранения результатов обхода
     */
    private void depthFirstRecursive(TreeNode<T> node, List<T> result) {
        if (node != null) {
            // Прямой обход: корень -> левый -> правый
            result.add(node.data);
            depthFirstRecursive(node.left, result);
            depthFirstRecursive(node.right, result);
        }
    }

    /**
     * Выполняет обход дерева в глубину без использования рекурсии.
     * Использует стек для имитации рекурсии. Прямой порядок.
     *
     * @return список элементов в порядке прямого обхода.
     */
    public List<T> depthFirstTraversalIterative() {
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