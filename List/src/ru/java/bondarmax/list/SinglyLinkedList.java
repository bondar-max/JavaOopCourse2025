package ru.java.bondarmax.list;

import ru.java.bondarmax.list_node.ListNode;

import java.util.NoSuchElementException;

public class SinglyLinkedList {
    private ListNode head;

    public SinglyLinkedList(){
        head = null;
    }

	/*
	 * получение размера списка
	 */
    public int getLinkedListSize() {
        int elementCounter = 0;
        ListNode currentElement = head;

        while (currentElement != null) {
            elementCounter++;
            currentElement = currentElement.getNext();
        }

        return elementCounter;
    }

    /*
     * получение значения первого элемента
     */
    public Integer getFirstListElement() {
        if (head == null) {
            return null;
        }

        return head.getValue();
    }

    /*
     * изменение значения по указанному индексу. Изменение значения по индексу пусть выдает старое значение.
     */
    public Integer updateElementValue(int targetIndex, int newElementValue) {
        if (targetIndex < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть неотрицательным. Переданное значение: index = %d", targetIndex));
        }

        ListNode currentElement = head;

        for (int currentIndex = 0; currentElement != null; currentIndex++) {
            if (currentIndex == targetIndex) {
                int previousValue = currentElement.getValue();
                currentElement.setValue(newElementValue);
                return previousValue;
            }

            currentElement = currentElement.getNext();
        }

        return null;
    }

    /*
     * получение по указанному индексу.
     */
    public Integer getElementByIndex(int targetIndex) {
        if (targetIndex < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть неотрицательным. Переданное значение: index = %d", targetIndex));
        }

        ListNode currentElement = head;

        for (int currentIndex = 0; currentElement != null; currentIndex++) {
            if (currentIndex == targetIndex) {
                return currentElement.getValue();
            }

            currentElement = currentElement.getNext();
        }

        return null;
    }


    /*
     * удаление элемента по индексу, пусть выдает значение элемента
     */
    public Integer removeElementByIndex(int targetIndex) {
        if (targetIndex < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть неотрицательным. Переданное значение: index = %d", targetIndex));
        }

        if (head == null) {
            return null;
        }

        if (targetIndex == 0) {
            int removedValue = head.getValue();
            head = head.getNext();
            return removedValue;
        }

        ListNode currentElement = head;
        int currentIndex = 0;

        for (; currentElement != null && currentElement.getNext() != null && currentIndex < targetIndex - 1; currentIndex++) {
            currentElement = currentElement.getNext();
        }

        if (currentElement == null || currentElement.getNext() == null) {
            throw new IndexOutOfBoundsException(String.format("Index выходит за пределы списка. Текущая длина: %d. Переданное значение: index = %d", currentIndex + 1, targetIndex));
        }

        int removedValue = currentElement.getNext().getValue();
        currentElement.setNext(currentElement.getNext().getNext());

        return removedValue;
    }

    /*
     * вставка элемента в начало
     */
    public void insertElementAtStart(int newElementValue) {
        ListNode newNode = new ListNode(newElementValue);
        newNode.setNext(head);
        head = newNode;
    }

    /*
     * вставка элемента по индексу
     */
    public void insertElementAtIndex(int targetIndex, int newElementValue) {
        if (targetIndex < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть неотрицательным. Переданное значение: index = %d", targetIndex));
        }

        if (targetIndex == 0) {
            insertElementAtStart(newElementValue);
        }

        ListNode newNode = new ListNode(newElementValue);
        ListNode currentElement = head;
        int currentIndex = 0;

        while (currentElement != null && currentIndex < targetIndex - 1) {
            currentElement = currentElement.getNext();
            currentIndex++;
        }

        if (currentElement == null) {
            throw new IndexOutOfBoundsException(String.format("Index выходит за пределы списка. Текущая длина: %d. Переданное значение: index = %d", currentIndex + 1, targetIndex));
        }

        newNode.setNext(currentElement.getNext());
        currentElement.setNext(newNode);
    }

    /*
     * удаление узла по значению, пусть выдает true, если элемент был удален
     */
    public boolean removeElementByValue(int targetValue) {
        if (head == null) {
            return false;
        }

        if (head.getValue() == targetValue) {
            head = head.getNext();
            return true;
        }

        ListNode currentElement = head;

        while (currentElement.getNext() != null) {
            if (currentElement.getNext().getValue() == targetValue) {
                currentElement.setNext(currentElement.getNext().getNext());
                return true;
            }

            currentElement = currentElement.getNext();
        }

        return false;
    }

    /*
     * удаление первого элемента, пусть выдает значение элемента
     */
    public int removeFirstElement() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Список пуст, невозможно удалить элемент");
        }

        int removedValue = head.getValue();
        head = head.getNext();

        return removedValue;
    }

    /*
     * разворот списка за линейное время
     */

    /*
     * копирование списка
     */

}
