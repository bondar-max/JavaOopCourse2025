package ru.java.bondarmax.matrix_main;

import ru.java.bondarmax.matrix.Matrix;
import ru.java.bondarmax.vector.Vector;

public class Main {
    public static void main(String[] args) {
        // Создание матрицы через конструктор нулей
        Matrix m1 = new Matrix(2, 3);
        System.out.println("Матрица нулей 2x3:" + m1);

        // Создание матрицы из двумерного массива
        double[][] array = {{1, 2, 3}, {4, 5, 6}};
        Matrix m2 = new Matrix(array);
        System.out.println("Матрица из массива:" + m2);

        // Создание матрицы из векторов
        Vector[] vectors = {
                new Vector(new double[]{1, 2, 3}),
                new Vector(new double[]{4, 5, 6})
        };
        Matrix m3 = new Matrix(vectors);
        System.out.println("Матрица из векторов:" + m3);

        Matrix m4 = new Matrix(m3);
        System.out.println("Матрица из векторов m4:" + m4);
    }
}
