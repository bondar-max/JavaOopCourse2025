package ru.java.bondarmax.hashtable;

import java.util.*;
import java.lang.reflect.Array;

public class HashTable<E> implements Collection<E> {
    // Поле для хранения массива списков элементов
    private final List<E>[] buckets;

    // Счетчик изменений коллекции
    private int modCount;

    // Поле для хранения текущего размера коллекции
    private int size;

    // Константа для размера по умолчанию
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Конструктор для создания хеш-таблицы.
     *
     * @param initialCapacity начальная ёмкость хеш-таблицы
     */
    public HashTable(int initialCapacity) {

        // Проверка аргумента на корректность
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Вместимость должна быть положительной, получено: " + initialCapacity);
        }

        // Создаем массив, но НЕ инициализируем все ячейки сразу
        //noinspection unchecked
        buckets = (List<E>[]) new List[initialCapacity];
    }

    /**
     * Конструктор по умолчанию, создающий хеш-таблицу с начальной ёмкостью 16.
     */
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Вычисляет индекс корзины для элемента.
     *
     * @param element элемент для вычисления индекса
     * @return индекс корзины для хранения элемента
     */
    private int getBucketIndex(Object element) {
        return element == null ? 0 : Math.abs(element.hashCode()) % buckets.length; // Для null элемента используем корзину 0
    }

    /**
     * Возвращает текущий размер коллекции.
     *
     * @return количество элементов в коллекции
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли коллекция.
     *
     * @return true, если коллекция пуста
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Проверяет наличие элемента в коллекции.
     *
     * @param o искомый объект
     * @return true, если элемент найден
     */
    @Override
    public boolean contains(Object o) {
        int index = getBucketIndex(o);

        return buckets[index] != null && buckets[index].contains(o);
    }

    /**
     * Преобразует коллекцию в массив объектов.
     *
     * @return массив объектов, содержащий элементы коллекции
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;

        for (List<E> bucket : buckets) {
            if (bucket != null) {
                for (E element : bucket) {
                    array[i] = element;
                    i++;
                }
            }
        }

        return array;
    }


    /**
     * Преобразует коллекцию в массив заданного типа.
     *
     * @param a массив для копирования элементов
     * @return массив заданного типа с элементами коллекции
     */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            //noinspection unchecked
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;

        for (List<E> bucket : buckets) {
            if (bucket != null) {
                for (E element : bucket) {
                    //noinspection unchecked
                    a[i] = (T) element;
                    i++;
                }
            }
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    /**
     * Добавляет элемент в хеш-таблицу.
     *
     * @param e добавляемый элемент
     * @return true, если элемент успешно добавлен
     */
    @Override
    public boolean add(E e) {
        int index = getBucketIndex(e);

        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }

        buckets[index].add(e);
        size++;
        modCount++;
        return true;
    }

    /**
     * Удаляет указанный объект из коллекции.
     *
     * @param o объект для удаления
     * @return true, если объект был успешно удалён
     */
    @Override
    public boolean remove(Object o) {
        int index = getBucketIndex(o);

        if (buckets[index] != null && buckets[index].remove(o)) {
            size--;
            modCount++;
            return true;
        }

        return false;
    }

    /**
     * Проверяет наличие всех элементов из указанной коллекции.
     *
     * @param c коллекция для проверки
     * @return true, если все элементы присутствуют
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Добавляет все элементы из указанной коллекции.
     *
     * @param c коллекция для добавления
     * @return true, если коллекция изменилась
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isModified = false;

        for (E e : c) {
            if (add(e)) {
                isModified = true;
            }
        }

        return isModified;
    }

    /**
     * Удаляет все элементы, присутствующие в указанной коллекции.
     *
     * @param c коллекция элементов для удаления
     * @return true, если коллекция изменилась
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false;

        for (List<E> bucket : buckets) {
            if (bucket != null && !bucket.isEmpty()) {
                int initialSize = bucket.size();

                if (bucket.removeAll(c)) {
                    size -= (initialSize - bucket.size());
                    modCount++;
                    isModified = true;
                }
            }
        }

        return isModified;
    }

    /**
     * Оставляет только те элементы, которые присутствуют в указанной коллекции.
     *
     * @param c коллекция для сравнения
     * @return true, если коллекция изменилась
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isModified = false;

        for (List<E> bucket : buckets) {
            if (bucket != null && !bucket.isEmpty()) {
                int initialBucketSize = bucket.size();

                if (bucket.retainAll(c)) {
                    size -= (initialBucketSize - bucket.size());
                    modCount++;
                    isModified = true;
                }
            }
        }

        return isModified;
    }

    /**
     * Очищает коллекцию, удаляя все элементы.
     */
    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (List<E> bucket : buckets) {
            if (bucket != null) {
                bucket.clear();
            }
        }

        size = 0;
        modCount++;
    }

    /**
     * Возвращает строковое представление хеш-таблицы.
     *
     * @return строковое представление в формате [элемент1, элемент2, ...]
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        boolean isFirstElement = true;

        for (E element : this) {
            if (!isFirstElement) {
                sb.append(", ");
            }

            sb.append(element);
            isFirstElement = false;
        }

        sb.append(']');
        return sb.toString();
    }

    /**
     * Возвращает итератор для перебора элементов коллекции.
     *
     * @return итератор коллекции
     */
    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<E> {
        private int bucketIndex;
        private Iterator<E> currentIterator;
        private final int expectedModCount;

        public HashTableIterator() {
            expectedModCount = modCount;
            findNextBucket();
        }

        private void findNextBucket() {
            // Ищем следующую непустую корзину
            int i = bucketIndex;

            while (i < buckets.length) {
                List<E> bucket = buckets[i];

                if (bucket != null && !bucket.isEmpty()) {
                    // нашли непустую корзину
                    currentIterator = bucket.iterator(); // Создаем итератор для этой корзины
                    bucketIndex = i; // Запоминаем позицию
                    return;
                }

                i++;
            }

            currentIterator = null;
        }

        @Override
        public boolean hasNext() {
            if (currentIterator != null && currentIterator.hasNext()) {
                return true;
            }

            if (bucketIndex < buckets.length) {
                bucketIndex++;
                findNextBucket();
            }

            return currentIterator != null && currentIterator.hasNext();
        }

        @Override
        public E next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("Коллекция была изменена во время итерации");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Нет больше элементов в коллекции");
            }

            E element = currentIterator.next();

            if (!currentIterator.hasNext()) {
                bucketIndex++;
                findNextBucket();
            }

            return element;
        }
    }
}
