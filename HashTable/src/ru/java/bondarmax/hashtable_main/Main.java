package ru.java.bondarmax.hashtable_main;

import ru.java.bondarmax.hashtable.HashTable;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Пример использования конструктора по умолчанию (ёмкость 16)
        HashTable<String> hashTable1 = new HashTable<>();

        // Пример использования конструктора с заданной ёмкостью
        HashTable<Integer> hashTable2 = new HashTable<>(32);

        // Добавление элементов с помощью метода add()
        hashTable1.add("Первый");
        hashTable1.add("Второй");
        hashTable1.add("Третий");

        // Добавление коллекции элементов с помощью addAll()
        List<String> list = Arrays.asList("Четвёртый", "Пятый");
        hashTable1.addAll(list);

        // Проверка размера коллекции
        System.out.println("Размер коллекции: " + hashTable1.size());

        // Проверка наличия элемента
        System.out.println("Содержит 'Первый': " + hashTable1.contains("Первый"));

        // Преобразование в массив
        Object[] array = hashTable1.toArray();
        System.out.println("Элементы в массиве: " + Arrays.toString(array));

        // Удаление элемента
        hashTable1.remove("Второй");
        System.out.println("Размер после удаления: " + hashTable1.size());

        // Проверка наличия всех элементов из коллекции
        List<String> checkList = Arrays.asList("Первый", "Третий");
        System.out.println("Содержит все элементы: " + hashTable1.containsAll(checkList));

        // Удаление всех элементов из коллекции
        hashTable1.removeAll(checkList);
        System.out.println("Размер после удаления всех элементов: " + hashTable1.size());

        // Очистка всей коллекции
        hashTable1.clear();

        // Пример использования итератора
        hashTable2.add(1);
        hashTable2.add(2);
        hashTable2.add(3);

        for (Integer integer : hashTable2) {
            System.out.println("Элемент: " + integer);
        }
    }
}