package ru.java.bondarmax.hashtable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    // Поле для хранения массива списков элементов
    private final List<List<E>> hashBuckets;

    // Поле для хранения текущего размера коллекции
    private int currentSize;

    // Константа для размера по умолчанию
    private static final int DEFAULT_HASH_TABLE_CAPACITY = 16;

    /**
     * Конструктор для создания хеш-таблицы с заданной начальной ёмкостью.
     *
     * @param initialCapacity начальная ёмкость хеш-таблицы
     */
    public HashTable(int initialCapacity) {
        hashBuckets = new ArrayList<>(initialCapacity);

        for (int i = 0; i < initialCapacity; i++) {
            hashBuckets.add(new LinkedList<>());
        }

        currentSize = 0;
    }

    /**
     * Конструктор по умолчанию, создающий хеш-таблицу с начальной ёмкостью 16.
     */
    public HashTable() {
        this(DEFAULT_HASH_TABLE_CAPACITY);
    }

    /**
     * Вычисляет хеш-код для элемента.
     *
     * @param e элемент для вычисления хеш-кода
     * @return индекс для хранения элемента
     */
    private int hash(E e) {
        if (hashBuckets.isEmpty()) {
            return 0;
        }

        return Math.abs(e.hashCode()) % hashBuckets.size();
    }

    /**
     * Возвращает текущий размер коллекции.
     *
     * @return количество элементов в коллекции
     */
    @Override
    public int size() {
        return currentSize;
    }

    /**
     * Проверяет, пуста ли коллекция.
     *
     * @return true, если коллекция пуста
     */
    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Проверяет наличие элемента в коллекции.
     *
     * @param o искомый объект
     * @return true, если элемент найден
     */
    @Override
    public boolean contains(Object o) {
        for (List<E> bucket : hashBuckets) {
            if (bucket.contains(o)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Преобразует коллекцию в массив объектов.
     *
     * @return массив объектов, содержащий элементы коллекции
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[currentSize];
        int index = 0;

        for (List<E> bucket : hashBuckets) {
            for (E element : bucket) {
                array[index++] = element;
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
        if (a.length < currentSize) {
            //noinspection unchecked
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), currentSize);
        }

        int index = 0;

        for (List<E> bucket : hashBuckets) {
            for (E element : bucket) {
                //noinspection unchecked
                a[index++] = (T) element;
            }
        }

        if (a.length > currentSize) {
            a[currentSize] = null;
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
        int index = hash(e);
        hashBuckets.get(index).add(e);
        currentSize++;

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
        for (List<E> bucket : hashBuckets) {
            if (bucket.remove(o)) {
                currentSize--;
                return true;
            }
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
        boolean modified = false;

        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }

        return modified;
    }

    /**
     * Удаляет все элементы, присутствующие в указанной коллекции.
     *
     * @param c коллекция элементов для удаления
     * @return true, если коллекция изменилась
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;

        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }

        return modified;
    }

    /**
     * Оставляет только те элементы, которые присутствуют в указанной коллекции.
     *
     * @param c коллекция для сравнения
     * @return true, если коллекция изменилась
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;

        for (List<E> bucket : hashBuckets) {
            for (Iterator<E> iterator = bucket.iterator(); iterator.hasNext(); ) {
                E element = iterator.next();

                if (!c.contains(element)) {
                    iterator.remove();
                    currentSize--;
                    modified = true;
                }
            }
        }

        return modified;
    }

    /**
     * Очищает коллекцию, удаляя все элементы.
     */
    @Override
    public void clear() {
        for (List<E> bucket : hashBuckets) {
            bucket.clear();
        }

        currentSize = 0;
    }

    /**
     * Возвращает итератор для перебора элементов коллекции.
     *
     * @return итератор коллекции
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int bucketIndex = 0;
            private Iterator<E> currentIterator;

            @Override
            public boolean hasNext() {
                while (bucketIndex < hashBuckets.size()) {
                    if (currentIterator == null) {
                        currentIterator = hashBuckets.get(bucketIndex).iterator();
                    }
                    if (currentIterator.hasNext()) {
                        return true;
                    }
                    currentIterator = null;
                    bucketIndex++;
                }
                return false;
            }

            @Override
            public E next() {
                return currentIterator.next();
            }
        };
    }
}
