package ru.java.bondarmax.matrix_main;

import ru.java.bondarmax.matrix.Matrix;
import ru.java.bondarmax.vector.Vector;

public class Main {
    public static void main(String[] args) {
        // Создание матрицы через конструктор нулей
        Matrix m1 = new Matrix(2, 3);
        System.out.printf("Матрица нулей 2x3:%n%s%n", m1);

        // Создание матрицы из двумерного массива
        double[][] array = {{1, 2, 3}, {4, 5}};
        Matrix m2 = new Matrix(array);
        System.out.printf("Матрица из массива:%n%s%n", m2);

        // Создание матрицы из векторов
        Vector[] vectors = {
                new Vector(new double[]{1, 2}),
                new Vector(new double[]{4, 5, 6})
        };
        Matrix m3 = new Matrix(vectors);
        System.out.printf("Матрица из векторов:%n%s%n", m3);

        Matrix m4 = new Matrix(m3);
        System.out.printf("Матрица из векторов m4:%n%s%n", m4);
    }
}
