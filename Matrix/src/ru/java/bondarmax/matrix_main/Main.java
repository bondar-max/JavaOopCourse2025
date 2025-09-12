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
        double[][] array = {{1, 2}, {3, 4, 5}};
        Matrix matrix2 = new Matrix(array);
        System.out.printf("Матрица из массива:%n%s%n", matrix2);

        // Создание матрицы из массива векторов
        Vector[] vectors = {new Vector(new double[]{1, 2}), new Vector(new double[]{4, 5, 6})};

        Matrix matrix3 = new Matrix(vectors);
        System.out.printf("Матрица из массива векторов:%n%s%n", matrix3);

        Matrix matrix4 = new Matrix(matrix3);
        System.out.printf("Матрица из векторов matrix4:%n%s%n", matrix4);

        // Заменена строки в матрице на другой вектор
        Matrix matrix5 = new Matrix(3, 3);
        Vector rowVector = new Vector(new double[]{7, 8, 9});

        matrix5.setRow(1, rowVector);

        System.out.println("Матрица после установки вектора: " + matrix5);

        // Сложение матриц
        Matrix sum = Matrix.getSum(matrix1, matrix2);
        System.out.println("Сумма: " + sum);

        // Вычитание
        Matrix diff = Matrix.getDifference(matrix1, matrix2);
        System.out.println("Разность: " + diff);

        // Транспонирование
        matrix2.transpose();
        System.out.println("Транспонированная matrix2: " + matrix2);

        // Умножение матрицы на вектор
        Vector vector = new Vector(new double[]{1, 2, 3});
        Vector resultVector = matrix1.multiply(vector);
        System.out.println("Результат умножения matrix1 на вектор {1, 2, 3}: " + resultVector);

        // Умножение матриц
        double[][] leftMatrix = {{11, 12}, {31, 14}};
        double[][] rightMatrix = {{15, 16}, {17, 18}};

        Matrix matrix6 = new Matrix(leftMatrix);
        Matrix matrix7 = new Matrix(rightMatrix);

        Matrix product = Matrix.getProduct(matrix6, matrix7);
        System.out.println("Произведение матриц 2x2: " + product);

        // Определитель
        double determinant = matrix6.calculateDeterminant();
        System.out.println("Определитель matrix6: " + determinant);

        // Получение строки и столбца
        Vector row = matrix6.getRow(0);
        Vector column = matrix6.getColumn(1);
        System.out.println("Первая строка matrix6: " + row);
        System.out.println("Второй столбец matrix6: " + column);

        // Умножение матрицы на скаляр
        Matrix scaledMatrix = new Matrix(matrix5);
        scaledMatrix.multiply(2.0);
        System.out.println("Матрица 5, умноженная на 2: " + scaledMatrix);
        System.out.println("Компонент матрицы 5: " + scaledMatrix.getComponent(0, 0));

        try {
            matrix5.setRow(5, new Vector(3));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}