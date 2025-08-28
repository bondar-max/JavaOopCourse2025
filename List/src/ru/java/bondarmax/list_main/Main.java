package ru.java.bondarmax.list_main;

import ru.java.bondarmax.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        // Создаем новый связный список
        SinglyLinkedList list = new SinglyLinkedList();

        // Добавляем элементы в начало списка
        list.insertElementAtStart(211);
        list.insertElementAtStart(79);
        list.insertElementAtStart(112);
        list.insertElementAtStart(10);
        list.insertElementAtStart(20);
        list.insertElementAtStart(30);

        System.out.println("После добавления элементов в начало:");
        System.out.println(list);

        // Получаем размер списка
        System.out.println("Размер списка: " + list.getLinkedListSize());

        // Получаем первый элемент
        System.out.println("Первый элемент: " + list.getFirstListElement());

        // Получаем головной узел
        System.out.println("Головной узел: " + list.getHead().getValue());

        // Вставляем элемент по индексу
        list.insertElementAtIndex(1, 25);
        System.out.println(System.lineSeparator() + "После вставки элемента 25 по индексу 1:");
        System.out.println(list);

        // Получаем элемент по индексу
        System.out.println("Элемент по индексу 2: " + list.getElementByIndex(2));

        // Обновляем значение по индексу
        System.out.println("Старое значение по индексу 1: " + list.updateElementValue(1, 99));
        System.out.println(System.lineSeparator() + "После обновления значения по индексу 1:");
        System.out.println(list);

        // Удаляем первый элемент
        System.out.println("Удаленный первый элемент: " + list.removeFirstElement());
        System.out.println(System.lineSeparator() + "После удаления первого элемента:");
        System.out.println(list);

        // Удаляем элемент по индексу
        System.out.println("Удаленный элемент по индексу 0: " + list.removeElementByIndex(0));
        System.out.println(System.lineSeparator() + "После удаления элемента по индексу 0:");
        System.out.println(list);

        // Удаляем элемент по значению
        System.out.println("Удален элемент 10: " + list.removeElementByValue(10));
        System.out.println(System.lineSeparator() + "После удаления элемента 10:");
        System.out.println(list);

        // Разворачиваем список
        list.reverseLinkedList();
        System.out.println(System.lineSeparator() + "После разворота списка:");
        System.out.println(list);

        // Копируем список
        SinglyLinkedList copiedList = new SinglyLinkedList();
        copiedList.setHead(list.copyLinkedList());
        System.out.println(System.lineSeparator() + "Скопированный список:");
        System.out.println(copiedList);
    }
}
