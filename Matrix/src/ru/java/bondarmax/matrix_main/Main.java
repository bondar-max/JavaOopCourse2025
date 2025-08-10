package ru.java.bondarmax.matrix_main;

import ru.java.bondarmax.matrix.Matrix;
import ru.java.bondarmax.vector.Vector;

public class Main {
    public static void main(String[] args) {
        // Создание матрицы n x m
        Matrix matrix1 = new Matrix(2, 3);
        System.out.printf("Матрица нулей 2x3:%n%s%n", matrix1);

        System.out.printf("Количество строк matrix1: %s%n", matrix1.getRowsQuantity());
        System.out.printf("Количество колонок matrix1: %s%n", matrix1.getColumnsQuantity());

        // Создание матрицы из двумерного массива
        double[][] array = {{1, 2,}, {3, 4, 5}};
        Matrix matrix2 = new Matrix(array);
        System.out.printf("Матрица из массива:%n%s%n", matrix2);

        // Создание матрицы из массива векторов
        Vector[] vectors = {
                new Vector(new double[]{1, 2}),
                new Vector(new double[]{4, 5, 6})
        };

        Matrix matrix3 = new Matrix(vectors);
        System.out.printf("Матрица из массива векторов:%n%s%n", matrix3);

        Matrix matrix4 = new Matrix(matrix3);
        System.out.printf("Матрица из векторов matrix4:%n%s%n", matrix4);
    }
}
