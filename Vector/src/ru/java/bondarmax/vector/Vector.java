package ru.java.bondarmax.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;
    private int size;

    // Конструктор с размерностью n
    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть положительной");
        }

        size = n;
        components = new double[n];
    }

    // Конструктор копирования
    public Vector(Vector other) {
        size = other.size;
        components = Arrays.copyOf(other.components, size);
    }

    // Конструктор с массивом значений
    public Vector(double[] values) {
        size = values.length;
        components = Arrays.copyOf(values, size);
    }

    // Конструктор с размерностью и массивом значений
    public Vector(int n, double[] values) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размерность вектора должна быть положительной");
        }

        size = n;
        components = new double[size];
        System.arraycopy(values, 0, components, 0, Math.min(values.length, size));
    }

    // Получение размерности
    public int getSize() {
        return size;
    }

    // Получение компоненты по индексу
    public double getComponent(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы вектора");
        }

        return components[index];
    }

    // Установка компоненты по индексу
    public void setComponent(int index, double value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы вектора");
        }

        components[index] = value;
    }

    // Прибавление вектора
    public void add(Vector other) {
        int maxSize = Math.max(size, other.size);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < size ? components[i] : 0;
            double b = i < other.size ? other.components[i] : 0;
            result[i] = a + b;
        }

        size = maxSize;
        components = result;
    }

    // Вычитание вектора
    public void subtract(Vector other) {
        int maxSize = Math.max(size, other.size);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < size ? components[i] : 0;
            double b = i < other.size ? other.components[i] : 0;
            result[i] = a - b;
        }

        size = maxSize;
        components = result;
    }

    // Умножение на скаляр
    public void multiply(double scalar) {
        for (int i = 0; i < size; i++) {
            components[i] *= scalar;
        }
    }

    // Разворот вектора
    public void reverse() {
        multiply(-1);
    }

    // Длина вектора
    public double length() {
        double sum = 0;

        for (double comp : components) {
            sum += comp * comp;
        }

        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        if (size == 0) return "{}";

        StringBuilder result = new StringBuilder("{");

        for (int i = 0; i < size; i++) {
            result.append(components[i]);
            if (i < size - 1) {
                result.append(", ");
            }
        }

        result.append("}");
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        Vector other = (Vector) obj;

        if (size != other.size){
            return false;
        }

        return Arrays.equals(components, other.components);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(components);
        result = 31 * result + size;
        return result;
    }

    // Реализация статических методов
    public static Vector add(Vector v1, Vector v2) {
        int maxSize = Math.max(v1.size, v2.size);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < v1.size ? v1.components[i] : 0;
            double b = i < v2.size ? v2.components[i] : 0;
            result[i] = a + b;
        }

        return new Vector(maxSize, result);
    }

    public static Vector subtract(Vector v1, Vector v2) {
        int maxSize = Math.max(v1.size, v2.size);
        double[] result = new double[maxSize];

        for (int i = 0; i < maxSize; i++) {
            double a = i < v1.size ? v1.components[i] : 0;
            double b = i < v2.size ? v2.components[i] : 0;
            result[i] = a - b;
        }

        return new Vector(maxSize, result);
    }

    public static double dotProduct(Vector v1, Vector v2) {
        int maxSize = Math.max(v1.size, v2.size);
        double sum = 0;

        for (int i = 0; i < maxSize; i++) {
            double a = i < v1.size ? v1.components[i] : 0;
            double b = i < v2.size ? v2.components[i] : 0;
            sum += a * b;
        }

        return sum;
    }
}