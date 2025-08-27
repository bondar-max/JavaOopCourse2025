package ru.java.bondarmax.list;

import ru.java.bondarmax.list_node.ListNode;

public class SinglyLinkedList {
    private ListNode head;

    public SinglyLinkedList(){
        head = null;
    }

	/*
	 * получение размера списка
	 */
    public int getListSize(){
        int listCount = 0;
        ListNode currentNode = head;

        while(currentNode != null){
            listCount++;
            currentNode = currentNode.getNext();
        }

        return listCount;
    }

    /*
     * получение значения первого элемента
     */
    public Integer getFirstElement(){
        if(head == null){
            return null;
        }

        return head.getValue();
    }

    /*
     * изменение значения по указанному индексу. Изменение значения по индексу пусть выдает старое значение.
     */
    public Integer setElement(int index, int newValue){
        if (index < 0){
            throw new IndexOutOfBoundsException(String.format("Index должен быть больше 0. Переданное значение: index = %d", index));
        }

        ListNode currentNode = head;

        for(int nodeIndex = 0; currentNode != null; nodeIndex++){
            if(nodeIndex == index){
                int oldValue = currentNode.getValue();
                currentNode.setValue(newValue);

                return oldValue;
            }

            currentNode = currentNode.getNext();
        }

        return null;
    }

    /*
     * получение по указанному индексу.
     */
    public Integer getElement(int index){
        if (index < 0){
            throw new IndexOutOfBoundsException(String.format("Index должен быть больше 0. Переданное значение: index = %d", index));
        }

        ListNode currentNode = head;

        for(int nodeIndex = 0; currentNode != null; nodeIndex++){
            if(nodeIndex == index){
                return currentNode.getValue();
            }

            currentNode = currentNode.getNext();
        }

        return null;
    }


    /*
     * удаление элемента по индексу, пусть выдает значение элемента
     */
    public Integer deleteAtIndex(int index){
        if (index < 0){
            throw new IndexOutOfBoundsException(String.format("Index должен быть больше 0. Переданное значение: index = %d", index));
        }

        if (head == null){
            return null;
        }

        if (index == 0){
            int deletedValue = head.getValue();
            head = null;

            return deletedValue;
        }

        ListNode currentNode = head;

        for(int nodeIndex = 0; currentNode != null && nodeIndex < index - 1; nodeIndex++){
                currentNode = currentNode.getNext();
            }

        if(currentNode == null || currentNode.getNext() == null){
            return null;
        }

        int deletedValue = currentNode.getNext().getValue();

        currentNode.setNext(currentNode.getNext().getNext());

        return deletedValue;
    }

    /*
     * вставка элемента в начало
     */
    public void addFirst(int value){
        ListNode newNode = new ListNode(value);
        newNode.setNext(head);
        head = newNode;
    }

    /*
     * вставка элемента по индексу
     */
    public void addAtIndex(int index, int value){
        if (index < 0){
            throw new IndexOutOfBoundsException(String.format("Index должен быть больше 0. Переданное значение: index = %d", index));
        }


        if (index == 0){
            addFirst(value);
        }

        ListNode newNode = new ListNode(value);
        ListNode currentNode = head;

        int nodeIndex = 0;

        for(; currentNode != null && nodeIndex < index - 1; nodeIndex++){
            currentNode = currentNode.getNext();
        }

        if(currentNode == null){
            throw new IndexOutOfBoundsException(String.format("Index выходит за пределы списка. Размер списка: %d. Переданное значение: index = %d", nodeIndex, index));
        }

        newNode.setNext(currentNode.getNext());
        currentNode.setNext(newNode.getNext());
    }

    /*
     * удаление узла по значению, пусть выдает true, если элемент был удален
     */

    /*
     * удаление первого элемента, пусть выдает значение элемента
     */

    /*
     * разворот списка за линейное время
     */

    /*
     * копирование списка
     */

}
