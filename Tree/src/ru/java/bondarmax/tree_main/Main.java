package ru.java.bondarmax.tree_main;

import ru.java.bondarmax.tree.BinarySearchTree;

public class Main {
    public static void main(String[] args) {
        String nl = System.lineSeparator();

        // Создаем бинарное дерево поиска для целых чисел
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        System.out.println("=== Демонстрация BinarySearchTree ===");

        // Проверяем пустое дерево
        System.out.println(nl + "1. Проверка пустого дерева:");
        System.out.println("Размер дерева: " + tree.getSize());
        System.out.println("Дерево пустое: " + tree.isEmpty());

        // Вставляем элементы
        System.out.println(nl + "2. Вставка элементов:");
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);
        tree.insert(10);
        tree.insert(25);
        tree.insert(35);
        tree.insert(45);

        System.out.println("Элементы 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45 вставлены в дерево");
        System.out.println("Размер дерева: " + tree.getSize());
        System.out.println("Дерево пустое: " + tree.isEmpty());

        // Проверяем наличие элементов
        System.out.println(nl + "3. Проверка наличия элементов:");
        System.out.println("Содержит 50: " + tree.contains(50));
        System.out.println("Содержит 25: " + tree.contains(25));
        System.out.println("Содержит 100: " + tree.contains(100));
        System.out.println("Содержит 15: " + tree.contains(15));

        // Обход в ширину
        System.out.println(nl + "4. Обход в ширину (BFS):");
        System.out.println("Результат: " + tree.breadthFirstTraversal());

        // Обход в глубину (рекурсивный)
        System.out.println(nl + "5. Обход в глубину - рекурсивный (DFS Pre-order):");
        System.out.println("Результат: " + tree.depthFirstTraversalRecursive());

        // Обход в глубину (итеративный)
        System.out.println(nl + "6. Обход в глубину - итеративный (DFS Pre-order):");
        System.out.println("Результат: " + tree.depthFirstTraversalIterative());

        // Удаляем элементы
        System.out.println(nl + "7. Удаление элементов:");

        // Удаляем лист (узел без детей)
        System.out.println("Удаляем лист 10: " + tree.remove(10));
        System.out.println("Обход в ширину после удаления 10: " + tree.breadthFirstTraversal());

        // Удаляем узел с одним ребенком
        System.out.println("Удаляем узел с одним ребенком 20: " + tree.remove(20));
        System.out.println("Обход в ширину после удаления 20: " + tree.breadthFirstTraversal());

        // Удаляем узел с двумя детьми
        System.out.println("Удаляем узел с двумя детьми 30: " + tree.remove(30));
        System.out.println("Обход в ширину после удаления 30: " + tree.breadthFirstTraversal());

        // Пытаемся удалить несуществующий элемент
        System.out.println("Пытаемся удалить несуществующий элемент 100: " + tree.remove(100));

        System.out.println("Текущий размер дерева: " + tree.getSize());

        // Проверяем состояние после удалений
        System.out.println(nl + "8. Проверка состояния после удалений:");
        System.out.println("Содержит 30: " + tree.contains(30));
        System.out.println("Содержит 25: " + tree.contains(25));
        System.out.println("Содержит 40: " + tree.contains(40));

        // Демонстрация с другими типами данных
        System.out.println(nl + "9. Демонстрация с строковыми данными:");
        BinarySearchTree<String> stringTree = new BinarySearchTree<>();

        stringTree.insert("apple");
        stringTree.insert("banana");
        stringTree.insert("cherry");
        stringTree.insert("date");
        stringTree.insert("elderberry");

        System.out.println("Строковое дерево - обход в ширину: " + stringTree.breadthFirstTraversal());
        System.out.println("Содержит 'banana': " + stringTree.contains("banana"));
        System.out.println("Содержит 'grape': " + stringTree.contains("grape"));

        // Обработка исключений
        System.out.println(nl + "10. Демонстрация обработки исключений:");
        try {
            tree.insert(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение при вставке null: " + e.getMessage());
        }

        try {
            tree.contains(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение при поиске null: " + e.getMessage());
        }

        try {
            tree.remove(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано исключение при удалении null: " + e.getMessage());
        }

        // Финальное состояние дерева
        System.out.println(nl + "11. Финальное состояние дерева:");
        System.out.println("Обход в ширину: " + tree.breadthFirstTraversal());
        System.out.println("Обход в глубину (рекурсивный): " + tree.depthFirstTraversalRecursive());
        System.out.println("Обход в глубину (итеративный): " + tree.depthFirstTraversalIterative());
        System.out.println("Размер дерева: " + tree.getSize());
    }
}