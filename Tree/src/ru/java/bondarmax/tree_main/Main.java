package ru.java.bondarmax.tree_main;

import ru.java.bondarmax.tree.BinarySearchTree;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String lineSeparator = System.lineSeparator();

        // Пример 1: Простое дерево с числами
        System.out.println("=== Пример 1: Дерево с числами ===");
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        System.out.println("Вставлен 50: " + tree.insert(50));
        System.out.println("Вставлен 30: " + tree.insert(30));
        System.out.println("Вставлен 70: " + tree.insert(70));
        System.out.println("Вставлен 20: " + tree.insert(20));
        System.out.println("Вставлен 40: " + tree.insert(40));
        System.out.println("Вставлен 40 (дубликат): " + tree.insert(40));

        System.out.println("Дерево: " + tree);
        System.out.println("Размер: " + tree.getSize());
        System.out.println("Пустое: " + tree.isEmpty());

        // Проверка элементов
        System.out.println("Содержит 30: " + tree.contains(30));
        System.out.println("Содержит 100: " + tree.contains(100));

        // Обходы
        List<Integer> list1 = new ArrayList<>();
        tree.traverseByLevelOrder(list1::add);
        System.out.println("Обход в ширину: " + list1);

        List<Integer> list2 = new ArrayList<>();
        tree.traversePreOrderRecursive(list2::add);
        System.out.println("Обход в глубину (рекурсивный): " + list2);

        List<Integer> list3 = new ArrayList<>();
        tree.traversePreOrderIterative(list3::add);
        System.out.println("Обход в глубину (итеративный): " + list3);

        // Удаление
        System.out.println("Удален 30: " + tree.remove(30));
        System.out.println("Удален 100 (не существует): " + tree.remove(100));
        System.out.println("После удаления: " + tree);

        // Пример 2: Дерево со строками
        System.out.println(lineSeparator + "=== Пример 2: Дерево со строками ===");
        BinarySearchTree<String> stringTree = new BinarySearchTree<>();

        System.out.println("Вставлен 'apple': " + stringTree.insert("apple"));
        System.out.println("Вставлен 'banana': " + stringTree.insert("banana"));
        System.out.println("Вставлен 'cherry': " + stringTree.insert("cherry"));

        System.out.println("Строковое дерево: " + stringTree);

        List<String> stringList = new ArrayList<>();
        stringTree.traversePreOrderIterative(stringList::add);
        System.out.println("Обход в глубину (итеративный): " + stringList);

        System.out.println("Содержит 'banana': " + stringTree.contains("banana"));
        System.out.println("Содержит 'grape': " + stringTree.contains("grape"));

        // Пример 3: Пустое дерево
        System.out.println(lineSeparator + "=== Пример 3: Пустое дерево ===");
        BinarySearchTree<Integer> emptyTree = new BinarySearchTree<>();
        System.out.println("Пустое дерево: " + emptyTree);
        System.out.println("Размер: " + emptyTree.getSize());
        System.out.println("Пустое: " + emptyTree.isEmpty());

        // Пример 4: Дерево с компаратором
        System.out.println(lineSeparator + "=== Пример 4: Дерево с компаратором ===");
        BinarySearchTree<Integer> reverseTree = new BinarySearchTree<>((a, b) -> b - a);

        System.out.println("Вставлен 50: " + reverseTree.insert(50));
        System.out.println("Вставлен 30: " + reverseTree.insert(30));
        System.out.println("Вставлен 70: " + reverseTree.insert(70));

        System.out.println("Обратное дерево: " + reverseTree);

        List<Integer> reverseList1 = new ArrayList<>();
        reverseTree.traversePreOrderRecursive(reverseList1::add);
        System.out.println("Обход в глубину (рекурсивный): " + reverseList1);

        List<Integer> reverseList2 = new ArrayList<>();
        reverseTree.traversePreOrderIterative(reverseList2::add);
        System.out.println("Обход в глубину (итеративный): " + reverseList2);

        // Пример 5: Дерево с null
        System.out.println(lineSeparator + "=== Пример 5: Дерево с null ===");
        BinarySearchTree<Integer> treeWithNull = new BinarySearchTree<>();

        System.out.println("Вставлен 50: " + treeWithNull.insert(50));
        System.out.println("Вставлен null: " + treeWithNull.insert(null));
        System.out.println("Вставлен 30: " + treeWithNull.insert(30));

        System.out.println("Дерево с null: " + treeWithNull);

        List<Integer> nullList = new ArrayList<>();
        treeWithNull.traversePreOrderIterative(nullList::add);
        System.out.println("Обход в глубину (итеративный): " + nullList);

        System.out.println("Содержит null: " + treeWithNull.contains(null));

        // Пример 6: Операции с Consumer
        System.out.println(lineSeparator + "=== Пример 6: Операции с Consumer ===");
        BinarySearchTree<Integer> smallTree = new BinarySearchTree<>();

        smallTree.insert(10);
        smallTree.insert(5);
        smallTree.insert(15);

        System.out.print("Элементы (рекурсивный): ");
        smallTree.traversePreOrderRecursive(value -> System.out.print(value + " "));
        System.out.println();

        System.out.print("Элементы (итеративный): ");
        smallTree.traversePreOrderIterative(value -> System.out.print(value + " "));
        System.out.println();

        int[] sum1 = {0};
        smallTree.traversePreOrderRecursive(value -> sum1[0] += value);
        System.out.println("Сумма (рекурсивный): " + sum1[0]);

        int[] sum2 = {0};
        smallTree.traversePreOrderIterative(value -> sum2[0] += value);
        System.out.println("Сумма (итеративный): " + sum2[0]);
    }
}