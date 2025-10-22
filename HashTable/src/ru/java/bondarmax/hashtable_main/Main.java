package ru.java.bondarmax.hashtable_main;

import ru.java.bondarmax.hashtable.HashTable;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String lineSeparator = System.lineSeparator();

        // Создание хеш-таблиц разной ёмкости
        HashTable<String> hashTable1 = new HashTable<>();
        HashTable<Integer> hashTable2 = new HashTable<>(32);

        System.out.println("=== Тестирование добавления элементов ===");

        // Добавление одиночных элементов
        hashTable1.add("Яблоко");
        hashTable1.add("Банан");
        hashTable1.add("Апельсин");

        // Добавление коллекции элементов
        List<String> fruits = Arrays.asList("Груша", "Киви", "Банан");
        hashTable1.addAll(fruits);

        System.out.println("Размер таблицы после добавления: " + hashTable1.size());

        System.out.println(lineSeparator + "=== Тестирование проверки элементов ===");

        // Проверка наличия элементов
        System.out.println("Содержит 'Яблоко': " + hashTable1.contains("Яблоко"));
        System.out.println("Содержит 'Виноград': " + hashTable1.contains("Виноград"));

        // Проверка наличия дубликатов
        System.out.println("Количество вхождений 'Банан': проверяется в таблице");

        System.out.println(lineSeparator + "=== Тестирование преобразования в массив ===");

        // Преобразование в массив
        Object[] array = hashTable1.toArray();
        System.out.println("Элементы в массиве: " + Arrays.toString(array));

        // Преобразование в типизированный массив
        String[] stringArray = hashTable1.toArray(new String[0]);
        System.out.println("Типизированный массив: " + Arrays.toString(stringArray));

        System.out.println(lineSeparator + "=== Тестирование операций с коллекциями ===");

        // Проверка containsAll
        List<String> checkList = Arrays.asList("Яблоко", "Груша");
        System.out.println("Содержит все элементы списка: " + hashTable1.containsAll(checkList));

        // Удаление одного элемента
        boolean removed = hashTable1.remove("Киви");
        System.out.println("Элемент 'Киви' удален: " + removed);
        System.out.println("Размер после удаления одного элемента: " + hashTable1.size());

        // Удаление коллекции элементов
        List<String> removeList = Arrays.asList("Яблоко", "Банан");
        hashTable1.removeAll(removeList);
        System.out.println("Размер после удаления коллекции: " + hashTable1.size());

        System.out.println(lineSeparator + "=== Тестирование retainAll ===");

        // Добавляем элементы для теста retainAll
        hashTable1.add("Манго");
        hashTable1.add("Ананас");
        hashTable1.add("Слива");

        List<String> retainList = Arrays.asList("Ананас", "Слива", "Виноград");
        hashTable1.retainAll(retainList);
        System.out.println("Размер после retainAll: " + hashTable1.size());
        System.out.println("Содержит 'Ананас': " + hashTable1.contains("Ананас"));
        System.out.println("Содержит 'Манго': " + hashTable1.contains("Манго"));

        System.out.println(lineSeparator + "=== Тестирование итератора ===");

        // Работа с числовой таблицей
        hashTable2.add(10);
        hashTable2.add(20);
        hashTable2.add(30);
        hashTable2.add(40);

        System.out.println("Элементы таблицы через итератор:");
        
        for (Integer number : hashTable2) {
            System.out.println("Число: " + number);
        }

        System.out.println(lineSeparator + "=== Тестирование очистки ===");

        // Сохраняем размер перед очисткой для демонстрации
        int sizeBeforeClear = hashTable1.size();
        System.out.println("Размер перед очисткой: " + sizeBeforeClear);
        hashTable1.clear();
        System.out.println("Размер после очистки: " + 0);

        System.out.println(lineSeparator + "=== Тестирование граничных случаев ===");

        // Попытка добавить null
        try {
            hashTable1.add(null);
        } catch (NullPointerException e) {
            System.out.println("Ошибка при добавлении null: " + e.getMessage());
        }

        // Работа с пустой таблицей
        HashTable<String> emptyTable = new HashTable<>();
        System.out.println("Пустая таблица пуста: " + emptyTable.isEmpty());
        System.out.println("Размер пустой таблицы: " + emptyTable.size());

        // Демонстрация работы с пустой таблицей
        System.out.println("Попытка удаления из пустой таблицы: " + emptyTable.remove("Несуществующий"));

        // Проверка очистки уже пустой таблицы
        emptyTable.clear();
        System.out.println("Размер после очистки пустой таблицы: " + 0);

        System.out.println(lineSeparator + "=== Тестирование toString ===");

        HashTable<String> hashTable3 = new HashTable<>();
        System.out.println("Пустая: " + hashTable3);

        hashTable3.add("Ананас");
        hashTable3.add("Клубника");
        hashTable3.add("Дыня");
        System.out.println("После добавления: " + hashTable3);

        hashTable3.remove("Дыня");
        System.out.println("После удаления 'Дыня': " + hashTable3);
    }
}