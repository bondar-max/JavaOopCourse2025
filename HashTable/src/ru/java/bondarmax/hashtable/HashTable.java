package ru.java.bondarmax.hashtable;

import java.util.*;
import java.lang.reflect.Array;

public class HashTable<E> implements Collection<E> {
    // Поле для хранения массива списков элементов
    private final List<E>[] buckets;

    // Поле для хранения текущего размера коллекции
    private int size;

    // Константа для размера по умолчанию
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Конструктор для создания хеш-таблицы с заданной начальной ёмкостью.
     *
     * @param initialCapacity начальная ёмкость хеш-таблицы
     */
    public HashTable(int initialCapacity) {

        // Проверка аргумента на корректность
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity должен быть положительным, передано: " + initialCapacity);
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
    private int getBucketIndex(E element) {
        return Math.abs(element.hashCode()) % buckets.length;
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
        if (o == null) {
            throw new NullPointerException("Элемент не может быть null");
        }

        // Вычисляем индекс корзины на основе хеш-кода объекта
        int index = Math.abs(o.hashCode()) % buckets.length;

        // Проверяем только нужную корзину
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
    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("Элемент не может быть null");
        }

        int index = getBucketIndex(e);

        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }

        buckets[index].add(e);
        size++;
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
        if (o == null) {
            throw new NullPointerException("Элемент не может быть null");
        }

        // Вычисляем индекс корзины на основе хеш-кода объекта
        int index = Math.abs(o.hashCode()) % buckets.length;

        // Удаляем только из нужной корзины
        if (buckets[index] != null && buckets[index].remove(o)) {
            size--;
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
            add(e);
            isModified = true;
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

        Object[] array = toArray();
        return Arrays.toString(array);
    }

    /**
     * Возвращает итератор для перебора элементов коллекции.
     *
     * @return итератор коллекции
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int bucketIndex;
            private Iterator<E> currentIterator;

            private void findNextBucket() {
                // Ищем следующую непустую корзину
                while (bucketIndex < buckets.length) {
                    List<E> bucket = buckets[bucketIndex];
                    if (bucket != null && !bucket.isEmpty()) {
                        currentIterator = bucket.iterator();
                        return;
                    }
                    bucketIndex++;
                }
                currentIterator = null;
            }

            @Override
            public boolean hasNext() {
                // Если текущий итератор существует и есть следующий элемент
                if (currentIterator != null && currentIterator.hasNext()) {
                    return true;
                }

                bucketIndex++; // Переходим к следующей корзине
                findNextBucket();

                return currentIterator != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Нет больше элементов в коллекции");
                }
                return currentIterator.next();
            }
        };
    }
}
