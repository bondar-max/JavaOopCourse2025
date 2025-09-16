package ru.java.bondarmax.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private E[] elements; // Массив для хранения элементов
    private int size; // Текущий размер списка
    private int modificationCounter; // Счетчик модификаций

    /**
     * Конструктор по умолчанию
     */
    public ArrayList() {
        //noinspection unchecked
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        size = 0;
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
        size = 0;
        modificationCounter = 0;
    }

    /**
     * Конструктор копирования
     */
    public ArrayList(Collection<? extends E> collection) {
        //noinspection unchecked
        elements = (E[]) collection.toArray();
        size = elements.length;
        modificationCounter = 0;
    }

    public E[] getElements(){
        return  elements;
    }

    /**
     * @return возвращает количество элементов в списке
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуст ли список
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
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
        return Arrays.copyOf(elements, size);
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

        if (array.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(elements, size); // Создаем новый массив нужного размера
        }

        // Проверяем типы
        if (array.getClass().getComponentType() != elements.getClass().getComponentType()) {
            // Если типы не совпадают, создаем новый массив нужного типа
            // noinspection unchecked
            return (T[]) Arrays.copyOf(elements, size);
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(elements, 0, array, 0, size);

        // Если исходный массив больше размера коллекции
        if (array.length > size) {
            array[size] = null;
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
     * Уменьшает емкость списка до текущего размера
     */
    public void trimToSize() {
        if (size != elements.length) {
            elements = Arrays.copyOf(elements, size);
        }
    }

    /**
     * Добавляет элемент в конец списка
     *
     * @param element element whose presence in this collection is to be ensured
     */
    @Override
    public boolean add(E element) {
        ensureCapacity(size + 1);
        elements[size] = element;
        size++;
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
        if (index < size - 1) {
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        }

        elements[size--] = null;
    }

    /**
     * Проверяет, содержатся ли все элементы указанной коллекции в текущем списке
     *
     * @param inputCollection collection to be checked for containment in this list
     */
    @Override
    public boolean containsAll(Collection<?> inputCollection) {
        for (Object element : inputCollection) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Добавляет все элементы коллекции в конец списка
     *
     * @param inputCollection collection containing elements to be added to this collection
     */
    @Override
    public boolean addAll(Collection<? extends E> inputCollection) {
        int size = inputCollection.size();

        if (size == 0){
            return false;
        }

        ensureCapacity(this.size + size);

        //noinspection unchecked
        E[] inputElements = inputCollection.toArray((E[]) new Object[0]);

        System.arraycopy(inputElements, 0, elements, this.size, size);

        this.size += size;
        modificationCounter++;

        return true;
    }

    /**
     * Вставляет все элементы коллекции в список, начиная с указанного индекса
     *
     * @param insertPosition      index at which to insert the first element from the
     *                   specified collection
     * @param inputCollection collection containing elements to be added to this list
     */
    @Override
    public boolean addAll(int insertPosition, Collection<? extends E> inputCollection) {
        if (insertPosition < 0 || insertPosition > size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", size, insertPosition));
        }

        if (inputCollection.isEmpty()) {
            return false;
        }

        int expectedFinalSize = size + inputCollection.size();

        if (expectedFinalSize < 0) {
            throw new IllegalArgumentException("Превышен максимально допустимый размер списка");
        }

        //noinspection unchecked
        E[] newElements = inputCollection.toArray((E[]) new Object[0]); // Преобразуем коллекцию в массив
        int addedElementsCount = newElements.length;

        ensureCapacity(expectedFinalSize);

        // Сдвигаем существующие элементы
        System.arraycopy(elements, insertPosition, elements, insertPosition + addedElementsCount, size - insertPosition);

        // Копируем новые элементы
        System.arraycopy(newElements, 0, elements, insertPosition, addedElementsCount);

        // Обновляем размер и счетчик модификаций
        size += addedElementsCount;
        modificationCounter++;

        return true;
    }

    /**
     * Удаляет из текущей коллекции все элементы, которые содержатся в переданной коллекции.
     * Оставляет только те элементы, которых нет в переданной коллекции.
     *
     * @param inputCollection collection containing elements to be removed from this list
     */
    @Override
    public boolean removeAll(Collection<?> inputCollection) {
        boolean modified = false;

        for (int currentIndex = 0; currentIndex < size; currentIndex++) {
            if (inputCollection.contains(elements[currentIndex])) {
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
     * @param inputCollection collection containing elements to be retained in this list
     */
    @Override
    public boolean retainAll(Collection<?> inputCollection) {
        boolean modified = false;

        for (int currentIndex = 0; currentIndex < size; currentIndex++) {
            if (!inputCollection.contains(elements[currentIndex])) {
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
        Arrays.fill(elements, 0, size, null);
        size = 0;
        modificationCounter++;
    }

    /**
     * Возвращает элемент по указанному индексу
     *
     * @param index index of the element to return
     */
    @Override
    public E get(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, size));
        }

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
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, size));
        }

        E oldValue = elements[index];
        elements[index] = element;

        modificationCounter++;

        return oldValue;
    }

    /**
     * Вставляет элемент в список, начиная с указанного индекса
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, size));
        }

        ensureCapacity(size + 1);

        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;

        size++;
        modificationCounter++;
    }

    /**
     * Удаляет элемент по указанному индексу
     *
     * @param index the index of the element to be removed
     */
    @Override
    public E remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, size));
        }

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
            for (int i = 0; i < size; i++) {
                if (elements[i] == null){
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
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
            for (int currentIndex = size - 1; currentIndex >= 0; currentIndex--) {
                if (elements[currentIndex] == null) {
                    return currentIndex;
                }
            }
        } else {
            for (int currentIndex = size - 1; currentIndex >= 0; currentIndex--) {
                if (element.equals(elements[currentIndex])) {
                    return currentIndex;
                }
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder result = new StringBuilder();
        result.append("[");

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }

            result.append(elements[i]);
        }

        result.append("]");

        return result.toString();
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

    public int getIteratorExpectedModificationCount() {
        return ((ArrayListIterator) iterator()).getExpectedModificationCount();
    }

    public void setIteratorExpectedModificationCount(int expectedModificationCount) {
        ((ArrayListIterator) iterator()).setExpectedModificationCount(expectedModificationCount);
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

        public int getExpectedModificationCount(){
            return expectedModificationCount;
        }

        public void setExpectedModificationCount(int expectedModificationCount){
            this.expectedModificationCount = expectedModificationCount;
        }
        /**
         * Проверяет, есть ли еще элементы для перебора
         */
        @Override
        public boolean hasNext() {
            return currentIndex != size;
        }

        /**
         * @return Возвращает следующий элемент в последовательности
         */
        @Override
        public E next() {
            if (modificationCounter != expectedModificationCount) {
                throw new ConcurrentModificationException("Список был изменен во время итерации");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Достигнут конец списка");
            }

            E nextElement = elements[currentIndex];
            currentIndex++;

            return nextElement;
        }
    }
}