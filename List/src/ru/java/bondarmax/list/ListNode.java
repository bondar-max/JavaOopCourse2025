package ru.java.bondarmax.list;

public class ListNode<E> {
    private E value;
    private ListNode<E> next;

    public ListNode(E value) {
        this.value = value;
    }

    public ListNode(E value, ListNode<E> next) {
        this.value = value;
        this.next = next;
    }

    public ListNode<E> getNext() {
        return next;
    }

    public void setNext(ListNode<E> next) {
        this.next = next;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
