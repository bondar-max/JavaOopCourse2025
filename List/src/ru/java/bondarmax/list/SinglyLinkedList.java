package ru.java.bondarmax.list;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
    private static class ListNode<T> {
        private T value;
        private ListNode<T> next;

        private ListNode(T value) {
            this.value = value;
        }

        private ListNode(T value, ListNode<T> next) {
            this.value = value;
            this.next = next;
        }

        private ListNode<T> getNext() {
            return next;
        }

        private void setNext(ListNode<T> next) {
            this.next = next;
        }

        private T getValue() {
            return value;
        }

        private void setValue(T value) {
            this.value = value;
        }
    }

    private ListNode<T> head;
    private int size;

    public SinglyLinkedList() {
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст, невозможно получить первый элемент");
        }

        return head.getValue();
    }

    private ListNode<T> getNodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", size, index));
        }

        ListNode<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current;
    }

    public T getByIndex(int index) {
        return getNodeAt(index).getValue();
    }

    public T setByIndex(int index, T newValue) {
        ListNode<T> node = getNodeAt(index);
        T oldValue = node.getValue();
        node.setValue(newValue);
        return oldValue;
    }

    public T removeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", size, index));
        }

        if (index == 0) {
            return removeFirst();
        }

        ListNode<T> previousNode = getNodeAt(index - 1);
        ListNode<T> currentNode = previousNode.getNext();

        T removedValue = currentNode.getValue();
        previousNode.setNext(currentNode.getNext());
        size--;

        return removedValue;
    }

    public void insertAtStart(T newValue) {
        head = new ListNode<>(newValue, head);
        size++;
    }

    public void insertAtIndex(int index, T newValue) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", size, index));
        }

        if (index == 0) {
            insertAtStart(newValue);
            return;
        }

        ListNode<T> previousElement = getNodeAt(index - 1);
        previousElement.setNext(new ListNode<>(newValue, previousElement.getNext()));
        size++;
    }

    public boolean removeByValue(T targetValue) {
        if (head == null) {
            return false;
        }

        // Если удаляем первый элемент
        if (head.getValue().equals(targetValue)) {
            head = head.getNext();
            size--;
            return true;
        }

        ListNode<T> previousNode = head;  // Начинаем с головы
        ListNode<T> currentNode = head.getNext();  // Следующий за головой

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

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст, невозможно удалить элемент");
        }

        T removedValue = head.getValue();
        head = head.getNext();
        size--;
        return removedValue;
    }

    public void reverse() {
        ListNode<T> currentNode = head;
        ListNode<T> previousNode = null;
        ListNode<T> nextNode;

        while (currentNode != null) {
            nextNode = currentNode.getNext();
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        head = previousNode;
    }

    public SinglyLinkedList<T> copy() {
        SinglyLinkedList<T> newList = new SinglyLinkedList<>();

        if (head == null) {
            return newList;
        }

        newList.head = new ListNode<>(head.getValue());
        ListNode<T> newListCurrentNode = newList.head;
        ListNode<T> originalListCurrentNode = head.getNext();

        while (originalListCurrentNode != null) {
            newListCurrentNode.setNext(new ListNode<>(originalListCurrentNode.getValue(), null));
            newListCurrentNode = newListCurrentNode.getNext();
            originalListCurrentNode = originalListCurrentNode.getNext();
        }

        newList.size = size;
        return newList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        ListNode<T> currentNode = head;

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
}