package ru.java.bondarmax.arraylist_main;

import ru.java.bondarmax.arraylist.ArrayList;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static final String NEW_LINE = System.lineSeparator();

    public static void main(String[] args) {
        //  Конструктор по умолчанию
        System.out.println("--- Конструктор по умолчанию ---" + NEW_LINE);
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        System.out.println("Список после добавления элементов: " + list1);
        list1.trimToSize();
        System.out.println("После trimToSize: " + list1 + NEW_LINE);

        // Конструктор с указанием начальной емкости
        System.out.println("--- Конструктор с начальной емкостью ---" + NEW_LINE);
        ArrayList<String> list2 = new ArrayList<>(5);
        list2.add("X");
        list2.add("Y");
        list2.add("Z");
        System.out.println("Список с начальной емкостью 5: " + list2);
        list2.trimToSize();
        System.out.println("После trimToSize: " + list2);
        System.out.println("Размер массива после trimToSize: " + list2.size() + NEW_LINE);

        // Конструктор копирования
        System.out.println("--- Конструктор копирования ---" + NEW_LINE);
        List<String> originalList = Arrays.asList("Один", "Два", "Три");
        ArrayList<String> list3 = new ArrayList<>(originalList);
        System.out.println("Скопированный список: " + list3 + NEW_LINE);

        // Работа с итератором
        System.out.println("--- Работа с итератором ---" + NEW_LINE);
        ArrayList<String> list4 = new ArrayList<>(Arrays.asList("Альфа", "Бета", "Гамма"));
        Iterator<String> iterator = list4.iterator();

        list4.add("Дельта");

        try {
            iterator.next(); // Должно вызвать ConcurrentModificationException
        } catch (ConcurrentModificationException e) {
            System.out.println("Поймано исключение модификации: " + e.getMessage());

            // Переинициализируем итератор после модификации коллекции
            iterator = list4.iterator();
        }

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // Добавляем элементы
        list4.add("Последний");
        System.out.println("После добавления элементов: " + list4);

        // Добавляем несколько элементов
        List<String> newElements = Arrays.asList("Новый1", "Новый2");
        list4.addAll(newElements);
        System.out.println("После добавления нескольких элементов: " + list4);

        // Вставляем элементы по индексу
        list4.addAll(2, Arrays.asList("Вставленный1", "Вставленный2"));
        System.out.println("После вставки по индексу: " + list4);

        // Получаем размер
        System.out.println("Размер списка: " + list4.size());

        // Проверяем содержит ли список элементы
        System.out.println("Содержит 'Альфа': " + list4.contains("Альфа"));

        // Получаем элемент по индексу
        System.out.println("Элемент по индексу 2: " + list4.get(2));

        // Заменяем элемент
        String oldValue = list4.set(2, "Измененный");
        System.out.println("После замены элемента: " + list4);
        System.out.println("Старое значение: " + oldValue);

        // Удаляем элемент по индексу
        String removed = list4.remove(3);
        System.out.println("После удаления элемента: " + list4);
        System.out.println("Удаленный элемент: " + removed);

        // Удаляем все элементы коллекции
        list4.removeAll(newElements);
        System.out.println("После удаления всех элементов newElements: " + list4);

        // Оставляем только указанные элементы
        list4.retainAll(Arrays.asList("Альфа", "Измененный"));
        System.out.println("После retainAll: " + list4);

        // Проверяем пустой ли список.
        if (!list4.isEmpty()) {
            // Очищаем список
            list4.clear();
            System.out.println("После очистки: " + list4);
        }

        // Используем итератор
        list4.addAll(Arrays.asList("A", "B", "C"));
        System.out.println(NEW_LINE + "Используем итератор:");

        for (String item : list4) {
            System.out.print(item + " ");
        }

        System.out.println(NEW_LINE);

        // Преобразуем в массив
        String[] array = list4.toArray(new String[0]);
        System.out.println("Массив: " + Arrays.toString(array));

        // Получаем подпоследовательность
        List<String> subList = list4.subList(0, list4.size());
        System.out.println("Подпоследовательность: " + subList);
    }
}