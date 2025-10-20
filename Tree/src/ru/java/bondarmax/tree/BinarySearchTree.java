package ru.java.bondarmax.tree;

import java.util.*;
import java.util.function.Consumer;

// Класс бинарного дерева поиска
public class BinarySearchTree<E> {
    private TreeNode<E> root;
    private int size;
    private final Comparator<? super E> comparator;

    // Класс узла бинарного дерева
    private static class TreeNode<E> {
        private final E data;
        private TreeNode<E> left;
        private TreeNode<E> right;

        public TreeNode(E data) {
            this.data = data;
        }
    }

    /**
     * Конструктор без компаратора.
     * Элементы должны реализовывать интерфейс Comparable<T>
     */
    public BinarySearchTree() {
        this.comparator = null;
    }

    /**
     * Конструктор с компаратором.
     * Для сравнения элементов будет использоваться переданный компаратор.
     *
     * @param comparator компаратор для сравнения элементов
     */
    public BinarySearchTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Вставляет новый элемент в бинарное дерево поиска.
     * Если элемент уже существует, дерево не изменяется.
     *
     * @param data элемент для вставки
     */
    public boolean insert(E data) {
        if (root == null) {
            root = new TreeNode<>(data);
            size++;
            return true;
        }

        TreeNode<E> currentNode = root;
        TreeNode<E> parentNode = null;
        boolean isLeftChild = false;

        while (currentNode != null) {
            parentNode = currentNode;

            int comparison = compare(data, currentNode.data);

            if (comparison < 0) {
                currentNode = currentNode.left;
                isLeftChild = true;
            } else {
                // >= 0 - дубликаты идут в правое поддерево
                currentNode = currentNode.right;
                isLeftChild = false;
            }
        }

        // Вставляем новый узел
        TreeNode<E> newNode = new TreeNode<>(data);

        if (isLeftChild) {
            parentNode.left = newNode;
        } else {
            parentNode.right = newNode;
        }

        size++;
        return true;
    }


    /**
     * Проверяет, содержится ли элемент в дереве.
     *
     * @param data элемент для поиска
     * @return true если элемент найден, false в противном случае
     */
    public boolean contains(E data) {
        TreeNode<E> currentNode = root;

        while (currentNode != null) {
            int comparison = compare(data, currentNode.data);

            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }

        return false;
    }

    /**
     * Удаляет первое вхождение элемента из дерева.
     *
     * @param data элемент для удаления
     * @return true если элемент был найден и удален, false если элемент не найден
     */
    public boolean remove(E data) {
        TreeNode<E> currentNode = root;
        TreeNode<E> parentNode = null;
        boolean isLeftChild = false;

        // Поиск узла для удаления
        while (currentNode != null) {
            int comparison = compare(data, currentNode.data);

            if (comparison == 0) {
                removeNode(currentNode, parentNode, isLeftChild);
                return true;
            }

            parentNode = currentNode;

            if (comparison < 0) {
                currentNode = currentNode.left;
                isLeftChild = true;
            } else {
                currentNode = currentNode.right;
                isLeftChild = false;
            }
        }

        return false; // Узел не найден
    }

    /**
     * Вспомогательный метод для удаления узла.
     */
    private void removeNode(TreeNode<E> node, TreeNode<E> parentNode, boolean isLeftChild) {
        // Случай 1: Узел без детей
        if (node.left == null && node.right == null) {
            if (node == root) {
                root = null;
            } else if (isLeftChild) {
                parentNode.left = null;
            } else {
                parentNode.right = null;
            }
        }
        // Случай 2: Узел с одним ребенком (правым)
        else if (node.left == null) {
            if (node == root) {
                root = node.right;
            } else if (isLeftChild) {
                parentNode.left = node.right;
            } else {
                parentNode.right = node.right;
            }
        }
        // Случай 2: Узел с одним ребенком (левым)
        else if (node.right == null) {
            if (node == root) {
                root = node.left;
            } else if (isLeftChild) {
                parentNode.left = node.left;
            } else {
                parentNode.right = node.left;
            }
        }
        // Случай 3: Узел с двумя детьми
        else {
            // Находим преемника (минимальный в правом поддереве) и его родителя
            TreeNode<E> successorParent = node;
            TreeNode<E> successor = node.right;
            boolean isSuccessorLeftChild = false;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
                isSuccessorLeftChild = true;
            }

            // Перестраиваем ссылки вместо копирования данных
            if (isSuccessorLeftChild) {
                successorParent.left = successor.right;
            } else {
                successorParent.right = successor.right;
            }

            // Заменяем удаляемый узел преемником
            successor.left = node.left;
            successor.right = node.right;

            if (node == root) {
                root = successor;
            } else if (isLeftChild) {
                parentNode.left = successor;
            } else {
                parentNode.right = successor;
            }
        }

        size--;
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
     * @param consumer потребитель для обработки элементов
     */
    public void traverseByLevelOrder(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<E> current = queue.poll();
            consumer.accept(current.data);

            if (current.left != null) {
                queue.offer(current.left);
            }

            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }

    /**
     * Выполняет обход дерева в глубину с использованием рекурсии.
     * Использует прямой порядок: корень → левое поддерево → правое поддерево.
     *
     * @param consumer потребитель для обработки элементов
     */
    public void traversePreOrderRecursive(Consumer<E> consumer) {
        traversePreOrderRecursive(root, consumer);
    }

    /**
     * Рекурсивный вспомогательный метод для обхода в глубину.
     *
     * @param node     текущий обрабатываемый узел
     * @param consumer потребитель для обработки элементов
     */
    private void traversePreOrderRecursive(TreeNode<E> node, Consumer<E> consumer) {
        if (node != null) {
            // Прямой обход: корень -> левый -> правый
            consumer.accept(node.data);
            traversePreOrderRecursive(node.left, consumer);
            traversePreOrderRecursive(node.right, consumer);
        }
    }

    /**
     * Выполняет обход дерева в глубину без использования рекурсии.
     * Использует стек для имитации рекурсии. Прямой порядок.
     *
     * @param consumer потребитель для обработки элементов
     */
    public void traversePreOrderIterative(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<E> current = stack.pop();
            consumer.accept(current.data);

            // Сначала добавляем правого ребенка, потом левого
            // чтобы левый обрабатывался первым (LIFO)
            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }
    }

    /**
     * Вспомогательный метод для сравнения элементов с поддержкой null.
     */
    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        if (a == null && b == null) {
            return 0;
        } else if (a == null) {
            return -1; // null меньше любого не-null значения
        } else if (b == null) {
            return 1;  // любое не-null значение больше null
        }

        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<? super E>) a).compareTo(b);
        }
    }

    /**
     * Возвращает строковое представление дерева в виде линейного списка элементов.
     *
     * @return строковое представление дерева
     */
    @Override
    public String toString() {
        if (root == null) {
            return "BinarySearchTree []";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("BinarySearchTree [");

        // Используем обход в ширину для строкового представления
        traverseByLevelOrder(element -> {
            sb.append(element);
            sb.append(", ");
        });

        // Удаляем последнюю запятую и пробел
        if (sb.length() > "BinarySearchTree [".length()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]");
        return sb.toString();
    }
}