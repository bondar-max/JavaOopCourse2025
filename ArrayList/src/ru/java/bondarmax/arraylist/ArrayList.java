package ru.java.bondarmax.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private E[] elements; // Массив для хранения элементов
    private int listSize;     // Текущий размер списка
    private int modificationCounter; // Счетчик модификаций

    /**
     * Конструктор по умолчанию
     */
    public ArrayList() {
        //noinspection unchecked
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        listSize = 0;
        modificationCounter = 0;
    }

    /**
     * Конструктор с указанием начальной вместимости
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("InitialCapacity не может быть отрицательным: " + initialCapacity);
        }

        //noinspection unchecked
        elements = (E[]) new Object[initialCapacity];
        listSize = 0;
        modificationCounter = 0;
    }

    /**
     * Конструктор копирования
     */
    public ArrayList(Collection<? extends E> collection) {
        //noinspection unchecked
        elements = (E[]) collection.toArray();
        listSize = elements.length;
        modificationCounter = 0;
    }

    /**
     * @return возвращает количество элементов в списке
     */
    @Override
    public int size() {
        return listSize;
    }

    /**
     * Проверяет, пуст ли список
     */
    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    /**
     * Проверяет наличие элемента в списке
     *
     * @param element element whose presence in this list is to be tested
     */
    @Override
    public boolean contains(Object element) {
        return indexOf(element) != -1;
    }

    /**
     * @return возвращает итератор для перебора элементов списка
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * @return возвращает массив, содержащий все элементы списка
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, listSize);
    }

    /**
     * @param array the array into which the elements of this list are to
     *              be stored, if it is big enough; otherwise, a new array of the
     *              same runtime type is allocated for this purpose.
     * @param <T>   тип массива
     * @return возвращает массив указанного типа, содержащий все элементы списка
     */
    @Override
    public <T> T[] toArray(T[] array) {
        // Проверяем, что массив не null
        if (array == null) {
            throw new NullPointerException("Массив не может быть null");
        }

        if (array.length < listSize) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(elements, listSize); // Создаем новый массив нужного размера
        }

        // Проверяем типы
        if (array.getClass().getComponentType() != elements.getClass().getComponentType()) {
            // Если типы не совпадают, создаем новый массив нужного типа
            // noinspection unchecked
            return (T[]) Arrays.copyOf(elements, listSize);
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, array, 0, listSize);

        // Если исходный массив больше размера коллекции
        if (array.length > listSize) {
            array[listSize] = null;
        }

        return array;
    }

    /**
     * Увеличивает внутреннюю емкость массива, если она меньше требуемой.
     * Помогает избежать частых перераспределений памяти при добавлении большого количества элементов.
     */
    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > elements.length) {
            increaseCapacity(requiredCapacity);
        }
    }

    /**
     * Гарантирует, что емкость списка не меньше указанного значения
     */
    private void increaseCapacity(int requiredCapacity) {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        if (newCapacity < requiredCapacity) {
            newCapacity = requiredCapacity;
        }

        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Проверяет, находится ли индекс в допустимых пределах
     *
     * @param index индекс
     */
    private void checkIndex(int index) {
        if (index > listSize || index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, listSize));
        }
    }

    /**
     * Уменьшает емкость списка до текущего размера
     */
    public void trimToSize() {
        if (listSize != elements.length) {
            elements = Arrays.copyOf(elements, listSize);
        }
    }

    /**
     * Добавляет элемент в конец списка
     *
     * @param element element whose presence in this collection is to be ensured
     */
    @Override
    public boolean add(E element) {
        ensureCapacity(listSize + 1);
        elements[listSize++] = element;
        modificationCounter++;

        return true;
    }

    /**
     * Удаляет первое вхождение указанного элемента
     *
     * @param element element to be removed from this list, if present
     */
    @Override
    public boolean remove(Object element) {
        int index = indexOf(element);

        if (index == -1) {
            return false;
        }

        removeElementAt(index);

        return true;
    }

    /**
     * Удаляет элемент по указанному индексу
     *
     * @param index индекс элемента
     */
    private void removeElementAt(int index) {
        int elementsToMove = listSize - index - 1;

        if (elementsToMove > 0) {
            System.arraycopy(elements, index + 1, elements, index, elementsToMove);
        }

        elements[listSize - 1] = null; // Освобождаем память

        listSize--;
    }

    /**
     * Проверяет, содержатся ли все элементы указанной коллекции в текущем списке
     *
     * @param collection collection to be checked for containment in this list
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Добавляет все элементы коллекции в конец списка
     *
     * @param collection collection containing elements to be added to this collection
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return false;
        }

        if (collection.size() > (Integer.MAX_VALUE - listSize)) {
            throw new OutOfMemoryError("Список слишком большой");
        }

        ensureCapacity(listSize + collection.size());

        //noinspection unchecked
        E[] newElements = collection.toArray((E[]) new Object[0]);
        int newSize = newElements.length;

        ensureCapacity(listSize + newSize);

        System.arraycopy(newElements, 0, elements, listSize, newSize);

        listSize += newSize;
        modificationCounter++;

        return true;
    }

    /**
     * Вставляет все элементы коллекции в список, начиная с указанного индекса
     *
     * @param index      index at which to insert the first element from the
     *                   specified collection
     * @param collection collection containing elements to be added to this list
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index < 0 || index > listSize) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, listSize));
        }

        if (collection.isEmpty()) {
            return false;
        }

        if (collection.size() > (Integer.MAX_VALUE - listSize)) {
            throw new OutOfMemoryError("Список слишком большой");
        }

        //noinspection unchecked
        E[] newElements = collection.toArray((E[]) new Object[0]);
        int newSize = newElements.length;

        ensureCapacity(listSize + newSize);

        System.arraycopy(elements, index, elements, index + newSize, listSize - index);
        System.arraycopy(newElements, 0, elements, index, newSize);

        listSize += newSize;
        modificationCounter++;

        return true;
    }

    /**
     * Удаляет из текущей коллекции все элементы, которые содержатся в переданной коллекции.
     * Оставляет только те элементы, которых нет в переданной коллекции.
     *
     * @param collection collection containing elements to be removed from this list
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;

        for (int currentIndex = 0; currentIndex < listSize; currentIndex++) {
            if (collection.contains(elements[currentIndex])) {
                removeElementAt(currentIndex);
                modified = true;
                currentIndex--; // сдвигаем индекс назад, так как размер уменьшился
            }
        }

        return modified;
    }

    /**
     * Оставляет в текущей коллекции только те элементы, которые есть в переданной коллекции.
     * Удаляет все элементы, которых нет в переданной коллекции.
     *
     * @param collection collection containing elements to be retained in this list
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;

        for (int currentIndex = 0; currentIndex < listSize; currentIndex++) {
            if (!collection.contains(elements[currentIndex])) {
                removeElementAt(currentIndex);
                modified = true;
                currentIndex--; // сдвигаем индекс назад, так как размер уменьшился
            }
        }

        return modified;
    }

    /**
     * Удаляет все элементы из списка
     */
    @Override
    public void clear() {
        for (int currentIndex = 0; currentIndex < listSize; currentIndex++) {
            elements[currentIndex] = null;
        }

        listSize = 0;
        modificationCounter++;
    }

    /**
     * Возвращает элемент по указанному индексу
     *
     * @param index index of the element to return
     */
    @Override
    public E get(int index) {
        checkIndex(index);

        return elements[index];
    }

    /**
     * Заменяет элемент по указанному индексу на новый
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     */
    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E oldValue = elements[index];
        elements[index] = element;

        modificationCounter++;

        return oldValue;
    }

    /**
     * Вставляет все элементы коллекции в список, начиная с указанного индекса
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    @Override
    public void add(int index, E element) {
        checkIndex(index);
        ensureCapacity(listSize + 1);

        System.arraycopy(elements, index, elements, index + 1, listSize - index);
        elements[index] = element;

        listSize++;
        modificationCounter++;
    }

    /**
     * Удаляет элемент по указанному индексу
     *
     * @param index the index of the element to be removed
     */
    @Override
    public E remove(int index) {
        checkIndex(index);

        E oldValue = elements[index];
        removeElementAt(index);

        modificationCounter++;

        return oldValue;
    }

    /**
     * Возвращает индекс первого вхождения указанного элемента
     *
     * @param element element to search for
     */
    @Override
    public int indexOf(Object element) {
        if (element == null) {
            for (int currentIndex = 0; currentIndex < listSize; currentIndex++) {
                if (elements[currentIndex] == null) {
                    return currentIndex;
                }
            }
        } else {
            for (int currentIndex = 0; currentIndex < listSize; currentIndex++) {
                if (element.equals(elements[currentIndex])) {
                    return currentIndex;
                }
            }
        }

        return -1;
    }

    /**
     * Возвращает индекс последнего вхождения указанного элемента
     *
     * @param element element to search for
     */
    @Override
    public int lastIndexOf(Object element) {
        if (element == null) {
            for (int currentIndex = listSize - 1; currentIndex >= 0; currentIndex--) {
                if (elements[currentIndex] == null) {
                    return currentIndex;
                }
            }
        } else {
            for (int currentIndex = listSize - 1; currentIndex >= 0; currentIndex--) {
                if (element.equals(elements[currentIndex])) {
                    return currentIndex;
                }
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }


    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex;
        private int expectedModificationCount;

        // Конструктор итератора
        public ArrayListIterator() {
            // Инициализируем начальные значения
            currentIndex = 0;  // Начинаем с начала списка
            expectedModificationCount = modificationCounter;  // Сохраняем текущее состояние модификаций
        }

        public void setCurrentIndex(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        public void setExpectedModificationCount(int newValue) {
            expectedModificationCount = newValue;
        }


        /**
         * Проверяет, есть ли еще элементы для перебора
         */
        @Override
        public boolean hasNext() {
            return currentIndex != listSize;
        }

        /**
         * @return Возвращает следующий элемент в последовательности
         */
        @Override
        public E next() {
            int currentIndex = this.currentIndex;

            if (modificationCounter != expectedModificationCount) {
                throw new ConcurrentModificationException("Список был изменен во время итерации");
            }

            if (currentIndex >= listSize) {
                throw new NoSuchElementException("Нет следующего элемента");
            }

            E[] elementsArray = ArrayList.this.elements;

            if (currentIndex >= elementsArray.length) {
                throw new ConcurrentModificationException("Список был изменен во время итерации");
            }

            currentIndex = currentIndex + 1;

            return elementsArray[currentIndex];
        }
    }
}