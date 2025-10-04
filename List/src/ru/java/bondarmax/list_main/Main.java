package ru.java.bondarmax.list_main;

import ru.java.bondarmax.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        // Создаем новый связный список
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        // Добавляем элементы в начало списка
        list.insertFirst(211);
        list.insertFirst(79);
        list.insertFirst(112);
        list.insertFirst(10);
        list.insertFirst(20);
        list.insertFirst(30);

        System.out.println("После добавления элементов в начало:");
        System.out.println(list);

        // Получаем размер списка
        System.out.println("Размер списка: " + list.getSize());

        // Получаем первый элемент
        System.out.println("Первый элемент: " + list.getFirst());

        // Вставляем элемент по индексу
        list.insertAtIndex(1, 25);
        System.out.println(System.lineSeparator() + "После вставки элемента 25 по индексу 1:");
        System.out.println(list);

        // Получаем элемент по индексу
        System.out.println("Элемент по индексу 2: " + list.getByIndex(2));

        // Обновляем значение по индексу
        System.out.println("Старое значение по индексу 1: " + list.setByIndex(1, 99));
        System.out.println(System.lineSeparator() + "После обновления значения по индексу 1:");
        System.out.println(list);

        // Удаляем первый элемент
        System.out.println("Удаленный первый элемент: " + list.removeFirst());
        System.out.println(System.lineSeparator() + "После удаления первого элемента:");
        System.out.println(list);

        // Удаляем элемент по индексу
        System.out.println("Удаленный элемент по индексу 0: " + list.removeByIndex(0));
        System.out.println(System.lineSeparator() + "После удаления элемента по индексу 0:");
        System.out.println(list);

        // Удаляем элемент по значению
        System.out.println("Удален элемент 10: " + list.removeByValue(10));
        System.out.println(System.lineSeparator() + "После удаления элемента 10:");
        System.out.println(list);

        // Разворачиваем список
        list.reverse();
        System.out.println(System.lineSeparator() + "После разворота списка:");
        System.out.println(list);

        // Копируем список
        SinglyLinkedList<Integer> copiedList = list.copy();
        System.out.println(System.lineSeparator() + "Скопированный список:");
        System.out.println(copiedList);
    }
}
