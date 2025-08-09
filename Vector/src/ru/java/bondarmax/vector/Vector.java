package ru.java.bondarmax.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    // Конструктор с размерностью n
    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть положительной");
        }

        components = new double[n];
    }

    // Конструктор копирования
    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    // Конструктор с массивом значений
    public Vector(double[] values) {
        if (values == null) {
            throw new NullPointerException("Массив значений не может быть null. Переданное значение: null");
        }

        if (values.length == 0) {
            throw new IllegalArgumentException("Размер вектора не может быть нулевым. Длина массива: " + values.length);
        }

        components = Arrays.copyOf(values, values.length);
    }

    // Конструктор с размерностью и массивом значений
    public Vector(int n, double[] values) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть положительной. Переданное значение: " + n);
        }

        if (values == null) {
            throw new NullPointerException("Массив значений не может быть null. Переданное значение: null");
        }

        // Создаем временный массив с копией значений
        double[] temp = Arrays.copyOf(values, Math.min(values.length, n));

        // Создаем итоговый массив нужной размерности
        components = Arrays.copyOf(temp, n);
    }


    // Получение компоненты по индексу
    public double getComponent(int index) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы вектора. Допустимый диапазон [0, " + components.length + "Переданное значение: " + index);
        }

        return components[index];
    }

    public int getSize() {
        return components.length;
    }


    // Установка компоненты по индексу
    public void setComponent(int index, double value) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы вектора. Допустимый диапазон [0, " + components.length + "Переданное значение: " + index);
        }

        components[index] = value;
    }

    // Прибавление вектора
    public void add(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Передаваемый вектор не может быть null");
        }

        // Если размеры векторов совпадают
        if (components.length == vector.components.length) {
            for (int i = 0; i < components.length; i++) {
                components[i] += vector.components[i];
            }
        } else {
            int maxSize = Math.max(components.length, vector.components.length);
            double[] result = new double[maxSize];

            for (int i = 0; i < maxSize; i++) {
                double a = i < components.length ? components[i] : 0;
                double b = i < vector.components.length ? vector.components[i] : 0;
                result[i] = a + b;
            }

            components = result;
        }
    }

    // Вычитание вектора
    public void subtract(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Передаваемый вектор не может быть null");
        }

        // Если размеры векторов совпадают
        if (components.length == vector.components.length) {
            for (int i = 0; i < components.length; i++) {
                components[i] -= vector.components[i];
            }
        } else {
            int maxSize = Math.max(components.length, vector.components.length);
            double[] result = new double[maxSize];

            for (int i = 0; i < maxSize; i++) {
                double a = i < components.length ? components[i] : 0;
                double b = i < vector.components.length ? vector.components[i] : 0;
                result[i] = a - b;
            }

            components = result;
        }
    }

    // Умножение на скаляр (масштабирование вектора)
    public void scale(double scalar) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= scalar;
        }
    }

    // Разворот вектора
    public void reverse() {
        scale(-1);
    }

    // Длина вектора
    public double getLength() {
        double sum = 0;

        for (double component : components) {
            sum += component * component;
        }

        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double component : components) {
            stringBuilder
                    .append(component)
                    .append(',')
                    .append(' ');
        }

        // Удаляем последнюю запятую и пробел, если есть компоненты
        if (components.length > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.append('}').toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Vector vector = (Vector) obj;

        if (components.length != vector.components.length) {
            return false;
        }

        return Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(components);
        result = 31 * result + components.length;
        return result;
    }

    // Реализация статических методов
    public static Vector getSum(Vector vector1, Vector vector2) {
        if (vector1 == null || vector2 == null) {
            throw new NullPointerException("Передаваемые вектора не могут быть null");
        }

        vector1.add(vector2);

        return new Vector(Math.max(vector1.components.length, vector2.components.length), vector1.components);
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        if (vector1 == null || vector2 == null) {
            throw new NullPointerException("Передаваемые вектора не могут быть null");
        }

        vector1.subtract(vector2);

        return new Vector(Math.max(vector1.components.length, vector2.components.length), vector1.components);
    }

    public static double scale(Vector vector1, Vector vector2) {
        if (vector1 == null || vector2 == null) {
            throw new NullPointerException("Передаваемые вектора не могут быть null");
        }

        double sum = 0;
        int minSize = Math.min(vector1.components.length, vector2.components.length);

        for (int i = 0; i < minSize; i++) {
            sum += vector1.components[i] * vector2.components[i];
        }

        return sum;
    }
}