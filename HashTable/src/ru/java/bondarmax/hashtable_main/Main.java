package ru.java.bondarmax.hashtable_main;

import ru.java.bondarmax.hashtable.HashTable;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String lineSeparator = System.lineSeparator();

        // Создание хеш-таблиц разной ёмкости
        HashTable<String> table1 = new HashTable<>();
        HashTable<Integer> table2 = new HashTable<>(32);

        System.out.println("=== Тестирование добавления элементов ===");

        // Добавление одиночных элементов
        table1.add("Яблоко");
        table1.add("Банан");
        table1.add("Апельсин");

        // Добавление коллекции элементов
        List<String> fruits = Arrays.asList("Груша", "Киви", "Банан");
        table1.addAll(fruits);

        System.out.println("Размер таблицы после добавления: " + table1.size());

        System.out.println(lineSeparator + "=== Тестирование проверки элементов ===");

        // Проверка наличия элементов
        System.out.println("Содержит 'Яблоко': " + table1.contains("Яблоко"));
        System.out.println("Содержит 'Виноград': " + table1.contains("Виноград"));

        // Проверка наличия дубликатов
        System.out.println("Количество вхождений 'Банан': проверяется в таблице");

        System.out.println(lineSeparator + "=== Тестирование преобразования в массив ===");

        // Преобразование в массив
        Object[] array = table1.toArray();
        System.out.println("Элементы в массиве: " + Arrays.toString(array));

        // Преобразование в типизированный массив
        String[] stringArray = table1.toArray(new String[0]);
        System.out.println("Типизированный массив: " + Arrays.toString(stringArray));

        System.out.println(lineSeparator + "=== Тестирование операций с коллекциями ===");

        // Проверка containsAll
        List<String> checkList = Arrays.asList("Яблоко", "Груша");
        System.out.println("Содержит все элементы списка: " + table1.containsAll(checkList));

        // Удаление одного элемента
        boolean removed = table1.remove("Киви");
        System.out.println("Элемент 'Киви' удален: " + removed);
        System.out.println("Размер после удаления одного элемента: " + table1.size());

        // Удаление коллекции элементов
        List<String> removeList = Arrays.asList("Яблоко", "Банан");
        table1.removeAll(removeList);
        System.out.println("Размер после удаления коллекции: " + table1.size());

        System.out.println(lineSeparator + "=== Тестирование retainAll ===");

        // Добавляем элементы для теста retainAll
        table1.add("Манго");
        table1.add("Ананас");
        table1.add("Слива");

        List<String> retainList = Arrays.asList("Ананас", "Слива", "Виноград");
        table1.retainAll(retainList);
        System.out.println("Размер после retainAll: " + table1.size());
        System.out.println("Содержит 'Ананас': " + table1.contains("Ананас"));
        System.out.println("Содержит 'Манго': " + table1.contains("Манго"));

        System.out.println(lineSeparator + "=== Тестирование итератора ===");

        // Работа с числовой таблицей
        table2.add(10);
        table2.add(20);
        table2.add(30);
        table2.add(40);

        System.out.println("Элементы таблицы через итератор:");
        for (Integer number : table2) {
            System.out.println("Число: " + number);
        }

        System.out.println(lineSeparator + "=== Тестирование очистки ===");

        // Сохраняем размер перед очисткой для демонстрации
        int sizeBeforeClear = table1.size();
        System.out.println("Размер перед очисткой: " + sizeBeforeClear);
        table1.clear();
        System.out.println("Размер после очистки: " + 0);
        System.out.println("Таблица пуста: " + table1.isEmpty());

        // Проверка, что элементы действительно удалены
        System.out.println("Содержит 'Ананас' после очистки: " + table1.contains("Ананас"));
        System.out.println("Содержит 'Слива' после очистки: " + table1.contains("Слива"));

        System.out.println(lineSeparator + "=== Тестирование граничных случаев ===");

        // Попытка добавить null
        try {
            table1.add(null);
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

        HashTable<String> table = new HashTable<>();
        System.out.println("Пустая: " + table);

        table.add("Ананас");
        table.add("Клубника");
        table.add("Дыня");
        System.out.println("После добавления: " + table);

        table.remove("Дыня");
        System.out.println("После удаления 'Дыня': " + table);
    }
}