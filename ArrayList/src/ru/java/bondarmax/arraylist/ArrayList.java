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

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(internalStorage, 0, array, 0, currentSize);

        // Если исходный массив больше размера коллекции
        if (array.length > currentSize) {
            array[currentSize] = null;
        }

        return array;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > internalStorage.length) {
            increaseCapacity(requiredCapacity);
        }
    }

    private void increaseCapacity(int requiredCapacity) {
        int oldCapacity = internalStorage.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        if (newCapacity < requiredCapacity) {
            newCapacity = requiredCapacity;
        }

        internalStorage = Arrays.copyOf(internalStorage, newCapacity);
    }

    private void checkIndexForAdd(int index) {
        if (index > currentSize || index < 0) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + currentSize);
        }
    }

    @Override
    public boolean add(E element) {
        ensureCapacity(currentSize + 1);
        internalStorage[currentSize++] = element;
        modificationCounter++;

        return true;
    }

    @Override
    public boolean remove(Object element) {
        int index = indexOf(element);

        if (index == -1) {
            return false;
        }

        removeElementAt(index);

        return true;
    }

    private void removeElementAt(int index) {
        int elementsToMove = currentSize - index - 1;

        if (elementsToMove > 0) {
            System.arraycopy(internalStorage, index + 1, internalStorage, index, elementsToMove);
        }

        internalStorage[currentSize - 1] = null; // Освобождаем память

        currentSize--;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        E[] newElements = collection.toArray((E[]) new Object[0]);
        int newSize = newElements.length;

        ensureCapacity(currentSize + newSize);
        System.arraycopy(newElements, 0, internalStorage, currentSize, newSize);

        currentSize += newSize;
        modificationCounter++;

        return newSize != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + currentSize);
        }

        E[] newElements = collection.toArray((E[]) new Object[0]);
        int newSize = newElements.length;

        ensureCapacity(currentSize + newSize);
        System.arraycopy(internalStorage, index, internalStorage, index + newSize, currentSize - index);
        System.arraycopy(newElements,0, internalStorage, index, newSize);

        currentSize += newSize;
        modificationCounter++;

        return newSize != 0;
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
        for (int i = 0; i < currentSize; i++) {
            internalStorage[i] = null;
        }

        currentSize = 0;
        modificationCounter++;
    }

    @Override
    public E get(int index) {
        checkIndexBounds(index);
        return internalStorage[index];
    }

    private void checkIndexBounds(int index) {
        if (index > currentSize || index < 0) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + currentSize);
        }
    }

    private String getOutOfBoundsMessage(int index) {
        return "Индекс: " + index + ", Размер: " + currentSize;
    }

    @Override
    public E set(int index, E element) {
        checkIndexBounds(index);

        E oldValue = internalStorage[index];
        internalStorage[index] = element;

        modificationCounter++;

        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);
        ensureCapacity(currentSize + 1);

        System.arraycopy(internalStorage, index, internalStorage, index + 1, currentSize - index);
        internalStorage[index] = element;

        currentSize++;
        modificationCounter++;
    }

    @Override
    public E remove(int index) {
        checkIndexBounds(index);

        E oldValue = internalStorage[index];
        removeElementAt(index);

        modificationCounter++;

        return oldValue;
    }

    @Override
    public int indexOf(Object element) {
        if (element == null) {
            for (int i = 0; i < currentSize; i++) {
                if (internalStorage[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                if (element.equals(internalStorage[i])) {
                    return i;
                }
            }
        }

        return -1;
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
