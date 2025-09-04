package ru.java.bondarmax.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E>{
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private E[] internalStorage; // Массив для хранения элементов
    private int currentSize;     // Текущий размер списка
    private int modificationCounter; // Счетчик модификаций

    // Конструктор по умолчанию
    public ArrayList() {
        //noinspection unchecked
        internalStorage = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        currentSize = 0;
        modificationCounter = 0;
    }

    // Конструктор с указанием начальной вместимости
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("InitialCapacity не может быть отрицательным: " + initialCapacity);
        }

        //noinspection unchecked
        internalStorage = (E[]) new Object[initialCapacity];
        currentSize = 0;
        modificationCounter = 0;
    }

    // Конструктор копирования
    public ArrayList(Collection<? extends E> collection) {
        //noinspection unchecked
        internalStorage = (E[]) collection.toArray();
        currentSize = internalStorage.length;
        modificationCounter = 0;
    }


    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public boolean contains(Object element) {
        return indexOf(element) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(internalStorage, currentSize);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        // Проверяем, что массив не null
        if (array == null) {
            throw new NullPointerException("Массив не может быть null");
        }

        if (array.length < currentSize) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(internalStorage, currentSize); // Создаем новый массив нужного размера
        }

        // Проверяем типы
        if (array.getClass().getComponentType() != internalStorage.getClass().getComponentType()) {
            // Если типы не совпадают, создаем новый массив нужного типа
            // noinspection unchecked
            return (T[]) Arrays.copyOf(internalStorage, currentSize);
        }

        System.arraycopy(internalStorage, 0, array, 0, currentSize);

        // Если исходный массив больше размера коллекции
        if (array.length > currentSize) {
            array[currentSize] = null;
        }

        return array;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }
}
