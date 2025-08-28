package ru.java.bondarmax.list;

import ru.java.bondarmax.list_node.ListNode;

import java.util.NoSuchElementException;

public class SinglyLinkedList {
    private ListNode head;
    private int listSize;

    public SinglyLinkedList() {
        head = null;
        listSize = 0;
    }

    public ListNode getHead() {
        return head;
    }

    public void setHead(ListNode newHead) {
        head = newHead;
        listSize = getLinkedListSize();
    }

    /**
     * Получение размера списка
     */
    public int getLinkedListSize() {
        return listSize;
    }

    /**
     * Получение значения первого элемента
     */
    public Integer getFirstListElement() {
        if (head == null) {
            return null;
        }

        return head.getValue();
    }

    /**
     * Изменение значения по указанному индексу. Изменение значения по индексу пусть выдает старое значение.
     *
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    public Integer updateElementValue(int targetIndex, int newElementValue) {
        if (targetIndex < 0 || targetIndex >= listSize) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d). Переданное значение: index = %d", listSize, targetIndex));
        }

        ListNode currentElement = head;

        for (int currentIndex = 0; currentIndex < targetIndex; currentIndex++) {
            currentElement = currentElement.getNext();
        }

        int previousValue = currentElement.getValue();
        currentElement.setValue(newElementValue);

        return previousValue;
    }

    /**
     * Получение по указанному индексу.
     *
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    public Integer getElementByIndex(int targetIndex) {
        if (targetIndex < 0 || targetIndex >= listSize) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d). Переданное значение: index = %d", listSize, targetIndex));
        }

        ListNode currentElement = head;

        for (int currentIndex = 0; currentIndex < targetIndex; currentIndex++) {
            currentElement = currentElement.getNext();
        }

        return currentElement.getValue();
    }

    /**
     * Удаление элемента по индексу, пусть выдает значение элемента
     *
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    public Integer removeElementByIndex(int targetIndex) {
        if (targetIndex < 0 || targetIndex >= listSize) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d). Переданное значение: index = %d", listSize, targetIndex));
        }

        if (head == null) {
            return null;
        }

        if (targetIndex == 0) {
            int removedValue = head.getValue();
            head = head.getNext();
            listSize--;

            return removedValue;
        }

        ListNode currentElement = head;

        for (int i = 0; i < targetIndex - 1; i++) {
            currentElement = currentElement.getNext();
        }

        int removedValue = currentElement.getNext().getValue();
        currentElement.setNext(currentElement.getNext().getNext());
        listSize--;

        return removedValue;
    }

    /**
     * Вставка элемента в начало
     */
    public void insertElementAtStart(int newElementValue) {
        ListNode newNode = new ListNode(newElementValue);
        newNode.setNext(head);
        head = newNode;
        listSize++;
    }

    /**
     * Вставка элемента по индексу
     *
     * @throws IndexOutOfBoundsException если индекс вне диапазона
     */
    public void insertElementAtIndex(int targetIndex, int newElementValue) {
        if (targetIndex < 0 || targetIndex > listSize) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне 0, %d). Переданное значение: index = %d", listSize, targetIndex));
        }

        if (targetIndex == 0) {
            insertElementAtStart(newElementValue);
            return;
        }

        ListNode newNode = new ListNode(newElementValue);
        ListNode currentElement = head;

        for (int i = 0; i < targetIndex - 1; i++) {
            currentElement = currentElement.getNext();
        }

        newNode.setNext(currentElement.getNext());
        currentElement.setNext(newNode);

        listSize++;
    }

    /**
     * Удаление узла по значению, пусть выдает true, если элемент был удален
     */
    public boolean removeElementByValue(int targetValue) {
        if (head == null) {
            return false;
        }

        if (head.getValue() == targetValue) {
            head = head.getNext();
            listSize--;

            return true;
        }

        ListNode currentElement = head;

        while (currentElement.getNext() != null) {
            if (currentElement.getNext().getValue() == targetValue) {
                currentElement.setNext(currentElement.getNext().getNext());
                listSize--;

                return true;
            }

            currentElement = currentElement.getNext();
        }

        return false;
    }

    /**
     * Удаление первого элемента, пусть выдает значение элемента
     *
     * @throws NoSuchElementException исключение если список пуст
     */
    public int removeFirstElement() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст, невозможно удалить элемент");
        }

        int removedValue = head.getValue();
        head = head.getNext();
        listSize--;

        return removedValue;
    }

    /**
     * Разворот списка за линейное время
     */
    public void reverseLinkedList() {
        ListNode currentNode = head;
        ListNode previousNode = null;
        ListNode nextNode;

        while (currentNode != null) {
            nextNode = currentNode.getNext();
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        head = previousNode;
    }

    /**
     * Копирование списка
     */
    public ListNode copyLinkedList() {
        if (head == null) {
            return null;
        }

        ListNode originalCurrentNode = head;
        ListNode newLinkedListHead = new ListNode(originalCurrentNode.getValue());
        ListNode newCurrentNode = newLinkedListHead;

        originalCurrentNode = originalCurrentNode.getNext();

        while (originalCurrentNode != null) {
            newCurrentNode.setNext(new ListNode(originalCurrentNode.getValue()));
            newCurrentNode = newCurrentNode.getNext();
            originalCurrentNode = originalCurrentNode.getNext();
        }

        return newLinkedListHead;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");

        ListNode current = head;

        while (current != null) {
            builder.append(current.getValue());

            if (current.getNext() != null) {
                builder.append(" -> ");
            }

            current = current.getNext();
        }

        builder.append("]");

        return builder.toString();
    }
}