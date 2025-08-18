package ru.java.bondarmax.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    // Конструктор с размерностью n
    public Vector(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("dimension должен быть положительным. Переданное значение: dimension = " + dimension);
        }

        components = new double[dimension];
    }

    // Конструктор копирования
    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    // Конструктор с массивом значений
    public Vector(double[] components) {
        if (components == null) {
            throw new NullPointerException("Переданное значение: null");
        }

        if (components.length == 0) {
            throw new IllegalArgumentException("components не может быть нулевым. Переданное значение: components = " + components.length);
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    // Конструктор с размерностью и массивом значений
    public Vector(int dimension, double[] components) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("dimension должен быть положительным. Переданное значение: dimension = " + dimension);
        }

        if (components == null) {
            throw new NullPointerException("Переданное значение: null");
        }

        this.components = Arrays.copyOf(components, dimension);
    }

    // Получение компоненты по индексу
    public double getComponent(int index) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("index должен быть [0, " + (components.length - 1) + ']' + " Переданное значение: index = " + index);
        }

        return components[index];
    }

    public int getSize() {
        return components.length;
    }

    // Установка компоненты по индексу
    public void setComponent(int index, double component) {
        if (index < 0 || index >= this.components.length) {
            throw new IndexOutOfBoundsException("index должен быть [0, " + (components.length - 1) + ']' + " Переданное значение: index = " + index);
        }

        components[index] = component;
    }

    private void addScaled(Vector vector, int sign) {
        if (vector == null) {
            throw new NullPointerException("vector не может быть null");
        }

        if (sign != 1 && sign != -1) {
            throw new IllegalArgumentException("sign должен быть +1.0 или -1.0. Переданное значение: sign = " + sign);
        }

        final int vectorLength = vector.components.length;

        if (components.length < vectorLength) {
            components = Arrays.copyOf(components, vectorLength);
        }

        for (int i = 0; i < vectorLength; i++) {
            components[i] += sign * vector.components[i];
        }
    }

    public void add(Vector vector) {
        addScaled(vector, 1);
    }

    public void subtract(Vector vector) {
        addScaled(vector, -1);
    }

    // Умножение на скаляр (масштабирование вектора)
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
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double component : components) {
            stringBuilder.append(component).append(", ");
        }

        stringBuilder.setLength(stringBuilder.length() - 2);

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

        return Arrays.equals(components, ((Vector) obj).components);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }

    // Реализация статических методов
    public static Vector getSum(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("vector1 не может быть null");
        }
        if (vector2 == null) {
            throw new NullPointerException("vector2 не может быть null");
        }

        int maxLength = Math.max(vector1.components.length, vector2.components.length);
        double[] result = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double a = (i < vector1.components.length) ? vector1.components[i] : 0.0;
            double b = (i < vector2.components.length) ? vector2.components[i] : 0.0;
            result[i] = a + b;
        }

        return new Vector(maxLength, result);
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("vector1 не может быть null");
        }
        if (vector2 == null) {
            throw new NullPointerException("vector2 не может быть null");
        }

        int maxLength = Math.max(vector1.components.length, vector2.components.length);
        double[] result = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double a = (i < vector1.components.length) ? vector1.components[i] : 0.0;
            double b = (i < vector2.components.length) ? vector2.components[i] : 0.0;
            result[i] = a - b;
        }

        return new Vector(maxLength, result);
    }

    public static double multiply(Vector vector1, Vector vector2) {
        if (vector1 == null) {
            throw new NullPointerException("vector1 не может быть null");
        }
        if (vector2 == null) {
            throw new NullPointerException("vector2 не может быть null");
        }

        final int minSize = Math.min(vector1.components.length, vector2.components.length);

        double sum = 0;

        for (int i = 0; i < minSize; i++) {
            sum += vector1.components[i] * vector2.components[i];
        }

        return sum;
    }

    public void resize(int newDimension) {
        if (newDimension <= 0) {
            throw new IllegalArgumentException("Размерность должна быть положительной. Передано: " + newDimension);
        }
        
        components = Arrays.copyOf(components, newDimension);
    }
}