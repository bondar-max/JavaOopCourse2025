package ru.java.bondarmax.list;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> {
    private ListNode<E> head;
    private int size;

    public SinglyLinkedList() {
    }

    public int getSize() {
        return size;
    }

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст, невозможно получить первый элемент");
        }

        return head.getValue();
    }

    private ListNode<E> getNodeAt(int index) {
        ListNode<E> currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }

        return currentNode;
    }

    public E getByIndex(int index) {
        checkIndex(index);
        return getNodeAt(index).getValue();
    }

    public E setByIndex(int index, E newValue) {
        checkIndex(index);

        ListNode<E> node = getNodeAt(index);
        E oldValue = node.getValue();
        node.setValue(newValue);
        return oldValue;
    }

    public E removeByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        ListNode<E> previousNode = getNodeAt(index - 1);
        ListNode<E> currentNode = previousNode.getNext();

        E removedValue = currentNode.getValue();
        previousNode.setNext(currentNode.getNext());
        size--;

        return removedValue;
    }

    public void insertAtStart(E newValue) {
        head = new ListNode<>(newValue, head);
        size++;
    }

    public void insertAtIndex(int index, E newValue) {
        checkIndex(index);

        if (index == 0) {
            insertAtStart(newValue);
            return;
        }

        ListNode<E> previousNode = getNodeAt(index - 1);
        previousNode.setNext(new ListNode<>(newValue, previousNode.getNext()));
        size++;
    }

    public boolean removeByValue(E targetValue) {
        if (head == null) {
            return false;
        }

        if (targetValue == null) {
            if (head.getValue() == null) {
                removeFirst();
                size--;
                return true;
            }
        }

        // Если удаляем первый элемент
        if (head.getValue().equals(targetValue)) {
            removeFirst();
            size--;
            return true;
        }

        ListNode<E> previousNode = head;  // Начинаем с головы
        ListNode<E> currentNode = head.getNext();  // Следующий за головой

        while (currentNode != null) {
            if (currentNode.getValue().equals(targetValue)) {
                // Удаляем текущий узел через предыдущий
                previousNode.setNext(currentNode.getNext());
                size--;
                return true;
            }

            // Перемещаемся к следующему узлу
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }

        return false;
    }

    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст, невозможно удалить элемент");
        }

        E removedValue = head.getValue();
        head = head.getNext();
        size--;
        return removedValue;
    }

    public void reverse() {
        ListNode<E> currentNode = head;
        ListNode<E> previousNode = null;

        while (currentNode != null) {
            ListNode<E> nextNode = currentNode.getNext();

            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        head = previousNode;
    }

    public SinglyLinkedList<E> copy() {
        SinglyLinkedList<E> newList = new SinglyLinkedList<>();

        if (head == null) {
            return newList;
        }

        newList.head = new ListNode<>(head.getValue());
        ListNode<E> newListCurrentNode = newList.head;
        ListNode<E> originalListCurrentNode = head.getNext();

        while (originalListCurrentNode != null) {
            newListCurrentNode.setNext(new ListNode<>(originalListCurrentNode.getValue()));
            newListCurrentNode = newListCurrentNode.getNext();
            originalListCurrentNode = originalListCurrentNode.getNext();
        }

        newList.size = size;
        return newList;
    }

    @Override
    public String toString() {
        if (head == null || size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");
        ListNode<E> currentNode = head;

        while (currentNode != null) {
            stringBuilder.append(currentNode.getValue()).append(", ");
            currentNode = currentNode.getNext();
        }

        if (size > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", size - 1, index));
        }
    }
}