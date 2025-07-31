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
            throw new NullPointerException("Массив значений не может быть null");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Размер вектора не может быть нулевым");
        }

        components = Arrays.copyOf(values, values.length);
    }

    // Конструктор с размерностью и массивом значений
    public Vector(int n, double[] values) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть положительной");
        }
        if (values == null) {
            throw new NullPointerException("Массив значений не может быть null");
        }

        // Создаем временный массив с копией значений
        double[] temp = Arrays.copyOf(values, Math.min(values.length, n));

        // Создаем итоговый массив нужной размерности
        components = Arrays.copyOf(temp, n);
    }


    // Получение компоненты по индексу
    public double getComponent(int index) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы вектора");
        }

        return components[index];
    }

    // Установка компоненты по индексу
    public void setComponent(int index, double value) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы вектора");
        }

        components[index] = value;
    }

    // Прибавление вектора
    public void add(Vector vector) {
        int maxSize = Math.max(components.length, vector.components.length);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < components.length ? components[i] : 0;
            double b = i < vector.components.length ? vector.components[i] : 0;
            result[i] = a + b;
        }

        components = result;
    }

    // Вычитание вектора
    public void subtract(Vector vector) {
        int maxSize = Math.max(components.length, vector.components.length);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < components.length ? components[i] : 0;
            double b = i < vector.components.length ? vector.components[i] : 0;
            result[i] = a - b;
        }

        components = result;
    }

    // Умножение на скаляр
    public void multiply(double scalar) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= scalar;
        }
    }

    // Разворот вектора
    public void reverse() {
        multiply(-1);
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
        if (components.length == 0) return "{}";

        StringBuilder result = new StringBuilder("{");

        for (int i = 0; i < components.length; i++) {
            result.append(components[i]);
            if (i < components.length - 1) {
                result.append(", ");
            }
        }

        result.append("}");
        return result.toString();
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
    public static Vector add(Vector v1, Vector v2) {
        int maxSize = Math.max(v1.components.length, v2.components.length);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < v1.components.length ? v1.components[i] : 0;
            double b = i < v2.components.length ? v2.components[i] : 0;
            result[i] = a + b;
        }

        return new Vector(maxSize, result);
    }

    public static Vector subtract(Vector v1, Vector v2) {
        int maxSize = Math.max(v1.components.length, v2.components.length);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < v1.components.length ? v1.components[i] : 0;
            double b = i < v2.components.length ? v2.components[i] : 0;
            result[i] = a - b;
        }

        return new Vector(maxSize, result);
    }

    public static double dotProduct(Vector v1, Vector v2) {
        int maxSize = Math.max(v1.components.length, v2.components.length);
        double sum = 0;

        for (int i = 0; i < maxSize; i++) {
            double a = i < v1.components.length ? v1.components[i] : 0;
            double b = i < v2.components.length ? v2.components[i] : 0;
            sum += a * b;
        }

        return sum;
    }
}