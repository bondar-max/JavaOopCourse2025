package ru.java.bondarmax.tree;

// Класс бинарного дерева поиска
public class BinarySearchTree<T extends Comparable<T>> {
    private TreeNode<T> root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Вставляет новый элемент в бинарное дерево поиска.
     * Если элемент уже существует, дерево не изменяется.
     *
     * @param data элемент для вставки
     * @throws IllegalArgumentException если передан null
     */
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
}
