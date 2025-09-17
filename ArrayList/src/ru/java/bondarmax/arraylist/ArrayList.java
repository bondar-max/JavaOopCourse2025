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
    }

    /**
     * Конструктор с указанием начальной вместимости
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Вместимость (initialCapacity) не может быть отрицательной: " + initialCapacity);
        }

        //noinspection unchecked
        elements = (E[]) new Object[initialCapacity];
    }

    /**
     * Конструктор копирования
     */
    public ArrayList(Collection<? extends E> collection) {
        //noinspection unchecked
        elements = (E[]) collection.toArray();
        size = elements.length;
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
     * @param element элемент, наличие которого в списке необходимо проверить
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
     * @param array Массив для сохранения элементов списка (при достаточной ёмкости) или
     *              новый массив аналогичного типа, если места недостаточно.
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
            return (T[]) Arrays.copyOf(elements, size, array.getClass());
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
            elements = Arrays.copyOf(elements, requiredCapacity);
        }
    }

    /**
     * Проверяет, нужна ли новая ёмкость, если текущая вместимость 0 — устанавливаем начальную, иначе увеличиваем вместимость ровно в 2 раза
     */
    private void increaseCapacity() {
        if (size == elements.length) {
            if (elements.length == 0) {
                //noinspection unchecked
                elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
            } else {
                int newCapacity = elements.length * 2;
                elements = Arrays.copyOf(elements, newCapacity);
            }
        }
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
     * @param element элемент, который необходимо включить в коллекцию
     */
    @Override
    public boolean add(E element) {
        increaseCapacity();
        elements[size] = element;
        size++;
        modificationCounter++;

        return true;
    }

    /**
     * Удаляет первое вхождение указанного элемента
     *
     * @param element элемент, который необходимо удалить из списка, если он есть
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
        if (size == 0) {
            throw new NoSuchElementException("Невозможно удалить элемент из пустого списка");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", size, index));
        }

        // Сдвигаем элементы влево
        if (index < size - 1) {
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        }

        // Обнуляем последний элемент
        size--;
        elements[size] = null;
        modificationCounter++;
    }

    /**
     * Проверяет, содержатся ли все элементы указанной коллекции в текущем списке
     *
     * @param collection коллекция, которую необходимо проверить на содержание в списке
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
     * @param collection коллекция, содержащая элементы, предназначенные для добавления в эту коллекцию
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return addAll(size, collection);
    }

    /**
     * Вставляет все элементы коллекции в список, начиная с указанного индекса
     *
     * @param index      индекс, по которому будет вставлен первый элемент из указанной коллекции
     * @param collection коллекция, содержащая элементы, которые должны быть добавлены в этот список
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", size, index)
            );
        }

        if (collection.isEmpty()) {
            return false;
        }

        int addedElementsCount = collection.size();
        int expectedFinalSize = size + addedElementsCount;

        ensureCapacity(expectedFinalSize);

        // Сдвигаем существующие элементы вправо
        for (int currentInsertIndex = size - 1; currentInsertIndex >= index; currentInsertIndex--) {
            elements[currentInsertIndex + addedElementsCount] = elements[currentInsertIndex];
        }

        // Добавляем элементы из коллекции напрямую
        int insertPositionCounter = 0;

        for (E element : collection) {
            elements[index + insertPositionCounter] = element;
            insertPositionCounter++;
        }

        size += addedElementsCount;
        modificationCounter++;

        return true;
    }

    /**
     * Удаляет из текущей коллекции все элементы, которые содержатся в переданной коллекции.
     * Оставляет только те элементы, которых нет в переданной коллекции.
     *
     * @param collection коллекция, содержащая элементы, которые должны быть удалены из этого списка
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isModified = false;

        for (int i = size - 1; i >= 0; i--) {
            if (collection.contains(elements[i])) {
                remove(i);
                isModified = true;
            }
        }

        return isModified;
    }

    /**
     * Оставляет в текущей коллекции только те элементы, которые есть в переданной коллекции.
     * Удаляет все элементы, которых нет в переданной коллекции.
     *
     * @param collection коллекция, содержащая элементы, которые должны быть сохранены в этом списке
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isModified = false;

        for (int i = size - 1; i >= 0; i--) {
            if (!collection.contains(elements[i])) {
                remove(i);
                isModified = true;
            }
        }

        return isModified;
    }

    /**
     * Удаляет все элементы из списка
     */
    @Override
    public void clear() {
        if (size == 0) {
            return; // если список пуст — ничего не делаем
        }

        Arrays.fill(elements, 0, size, null);
        size = 0;
        modificationCounter++;
    }

    /**
     * Возвращает элемент по указанному индексу
     *
     * @param index индекс элемента, который следует вернуть
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, size));
        }

        return elements[index];
    }

    /**
     * Заменяет элемент по указанному индексу на новый
     *
     * @param index   индекс элемента, который следует заменить
     * @param element элемент, который следует поместить в указанную позицию
     */
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", index, size));
        }

        E oldValue = elements[index];
        elements[index] = element;

        return oldValue;
    }

    /**
     * Вставляет элемент в список, начиная с указанного индекса
     *
     * @param index   индекс, по которому должен быть вставлен указанный элемент
     * @param element элемент для вставки
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
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
     * @param index индекс элемента, который должен быть удалён
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
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
     * @param element элемент, который необходимо найти
     */
    @Override
    public int indexOf(Object element) {
        for (int i = 0; i < size; i++) {
            if ((element == null && elements[i] == null) || (element != null && element.equals(elements[i]))) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Возвращает индекс последнего вхождения указанного элемента
     *
     * @param element элемент, который необходимо найти
     */
    @Override
    public int lastIndexOf(Object element) {
        for (int i = size - 1; i >= 0; i--) {
            if ((element == null && elements[i] == null) || (element != null && element.equals(elements[i]))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");
        stringBuilder.append(elements[0]);

        for (int i = 1; i < size; i++) {
            stringBuilder.append(", ").append(elements[i]);
        }

        return stringBuilder.append("]").toString();
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ArrayList<?> other = (ArrayList<?>) obj;

        if (size != other.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            Object o1 = elements[i];
            Object o2 = other.elements[i];

            if (!Objects.equals(o1, o2)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        for (int i = 0; i < size; i++) {
            Object obj = elements[i];
            result = 31 * result + (obj == null ? 0 : obj.hashCode());
        }

        return result;
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex;
        private final int expectedModificationsCounter = modificationCounter;  // Сохраняем текущее состояние модификаций

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
            if (modificationCounter != expectedModificationsCounter) {
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