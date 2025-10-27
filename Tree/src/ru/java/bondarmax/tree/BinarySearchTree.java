package ru.java.bondarmax.tree;

import java.util.*;
import java.util.function.Consumer;

// Класс бинарного дерева поиска
public class BinarySearchTree<E> {
    private TreeNode<E> root;
    private int size;
    private final Comparator<? super E> comparator;

    /**
     * Конструктор без компаратора.
     * Элементы должны реализовывать интерфейс Comparable<T>
     */
    public BinarySearchTree() {
        comparator = null;
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

            int compareResult = compare(data, currentNode.getData());

            if (compareResult < 0) {
                currentNode = currentNode.getLeft();
                isLeftChild = true;
            } else {
                // >= 0 - дубликаты идут в правое поддерево
                currentNode = currentNode.getRight();
                isLeftChild = false;
            }
        }

        // Вставляем новый узел
        TreeNode<E> newNode = new TreeNode<>(data);

        if (isLeftChild) {
            parentNode.setLeft(newNode);
        } else {
            parentNode.setRight(newNode);
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
            int comparison = compare(data, currentNode.getData());

            if (comparison == 0) {
                return true;
            }

            if (comparison < 0) {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
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
            int comparison = compare(data, currentNode.getData());

            if (comparison == 0) {
                removeNode(currentNode, parentNode, isLeftChild);
                return true;
            }

            parentNode = currentNode;

            if (comparison < 0) {
                currentNode = currentNode.getLeft();
                isLeftChild = true;
            } else {
                currentNode = currentNode.getRight();
                isLeftChild = false;
            }
        }

        return false; // Узел не найден
    }

    /**
     * Вспомогательный метод для удаления узла.
     */
    private void removeNode(TreeNode<E> node, TreeNode<E> parentNode, boolean isLeftChild) {
        // Случаи 1 и 2: Узел без детей или с одним ребенком
        if (node.getLeft() == null || node.getRight() == null) {
            TreeNode<E> child = (node.getLeft() != null) ? node.getLeft() : node.getRight();
            updateParentReference(node, parentNode, isLeftChild, child);
        }
        // Случай 3: Узел с двумя детьми
        else {
            // Находим преемника (минимальный в правом поддереве)
            TreeNode<E> successor = findMin(node.getRight());

            // Создаем новый узел с данными преемника
            TreeNode<E> replacementNode = new TreeNode<>(successor.getData());

            // Устанавливаем детей удаляемого узла
            replacementNode.setLeft(node.getLeft());

            // Создаем новое правое поддерево без преемника
            replacementNode.setRight(removeMin(node.getRight()));

            // Обновляем ссылку родительского узла
            updateParentReference(node, parentNode, isLeftChild, replacementNode);
        }

        size--;
    }

    /**
     * Находит узел с минимальным значением в поддереве.
     * В бинарном дереве поиска минимальный элемент всегда находится
     * в самом левом узле заданного поддерева.
     *
     * @param node корень поддерева для поиска
     * @return узел с минимальным значением в поддереве
     */
    private TreeNode<E> findMin(TreeNode<E> node) {
        TreeNode<E> currentNode = node;

        // Двигаемся влево, пока не найдем узел без левого ребенка
        while (currentNode.getLeft() != null) {
            currentNode = currentNode.getLeft();
        }

        return currentNode;
    }

    /**
     * Удаляет узел с минимальным значением из поддерева и возвращает новое поддерево.
     * Минимальный узел всегда является самым левым узлом в поддереве.
     *
     * @param node корень поддерева, из которого нужно удалить минимальный узел
     * @return новое поддерево без минимального узла
     */
    private TreeNode<E> removeMin(TreeNode<E> node) {
        if (node.getLeft() == null) {
            return node.getRight();
        }

        TreeNode<E> parent = node;
        TreeNode<E> current = node.getLeft();

        // Ищем минимальный узел и его родителя
        while (current.getLeft() != null) {
            parent = current;
            current = current.getLeft();
        }

        // Удаляем минимальный узел, подставляя его правого ребенка
        parent.setLeft(current.getRight());
        return node;
    }

    /**
     * Вспомогательный метод для обновления ссылки на узел в родительском узле.
     */
    private void updateParentReference(TreeNode<E> node, TreeNode<E> parentNode, boolean isLeftChild, TreeNode<E> child) {
        if (node == root) {
            root = child;
        } else if (isLeftChild) {
            parentNode.setLeft(child);
        } else {
            parentNode.setRight(child);
        }
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
    public void traverseBreadthFirst(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode<E> currentNode = queue.remove();
            consumer.accept(currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.offer(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.offer(currentNode.getRight());
            }
        }
    }

    /**
     * Выполняет обход дерева в глубину с использованием рекурсии.
     * Использует прямой порядок: корень → левое поддерево → правое поддерево.
     *
     * @param consumer потребитель для обработки элементов
     */
    public void traverseDepthFirstRecursive(Consumer<E> consumer) {
        traverseDepthFirstRecursive(root, consumer);
    }

    /**
     * Рекурсивный вспомогательный метод для обхода в глубину.
     *
     * @param node     текущий обрабатываемый узел
     * @param consumer потребитель для обработки элементов
     */
    private void traverseDepthFirstRecursive(TreeNode<E> node, Consumer<E> consumer) {
        if (node != null) {
            // Прямой обход: корень -> левый -> правый
            consumer.accept(node.getData());
            traverseDepthFirstRecursive(node.getLeft(), consumer);
            traverseDepthFirstRecursive(node.getRight(), consumer);
        }
    }

    /**
     * Выполняет обход дерева в глубину без использования рекурсии.
     * Использует стек для имитации рекурсии. Прямой порядок.
     *
     * @param consumer потребитель для обработки элементов
     */
    public void traverseDepthFirstIterative(Consumer<E> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode<E> current = stack.pop();
            consumer.accept(current.getData());

            // Сначала добавляем правого ребенка, потом левого
            // чтобы левый обрабатывался первым (LIFO)
            if (current.getRight() != null) {
                stack.push(current.getRight());
            }

            if (current.getLeft() != null) {
                stack.push(current.getLeft());
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

        // Если задан компаратор, то всегда используется он
        if (comparator != null) {
            return comparator.compare(a, b);
        }

        return ((Comparable<? super E>) a).compareTo(b);
    }

    /**
     * Возвращает строковое представление дерева в виде линейного списка элементов.
     *
     * @return строковое представление дерева
     */
    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // Используем обход в ширину для строкового представления
        traverseBreadthFirst(element -> sb.append(element).append(", "));

        // Удаляем последнюю запятую и пробел
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }

        sb.append(']');
        return sb.toString();
    }
}