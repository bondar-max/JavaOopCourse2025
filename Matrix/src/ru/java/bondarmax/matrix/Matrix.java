package ru.java.bondarmax.matrix;

import ru.java.bondarmax.vector.Vector;

public class Matrix {
    private final Vector[] rows;

    // Конструктор матрицы размера n x m, заполненной нулями
    public Matrix(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными. Передано: rows = " + rows + ", columns = " + columns);
        }

        this.rows = new Vector[rows];

        for (int i = 0; i < rows; i++) {
            this.rows[i] = new Vector(columns);
        }
    }

    // Конструктор копирования
    public Matrix(Matrix matrix) {
        final int rowsQuantity = matrix.getRowsQuantity();
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    // Конструктор из двумерного массива
    public Matrix(double[][] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Массив строк не может быть null или пустым");
        }

        final int rowsQuantity = array.length;
        int maxColumnsQuantity = 0;

        for (double[] row : array) {
            if (row == null) {
                throw new NullPointerException("Строка матрицы не может быть null");
            }
            maxColumnsQuantity = Math.max(maxColumnsQuantity, row.length);
        }

        if (maxColumnsQuantity == 0) {
            throw new IllegalArgumentException("Все строки пустые — матрица не может быть создана");
        }

        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(maxColumnsQuantity, array[i]);
        }
    }

    // Конструктор из массива векторов
    public Matrix(Vector[] vectors) {
        if (vectors == null || vectors.length == 0) {
            throw new IllegalArgumentException("Массив векторов не может быть null или пустым");
        }

        for (Vector v : vectors) {
            if (v == null) {
                throw new NullPointerException("Вектор не может быть null");
            }
        }

        final int rowsQuantity = vectors.length;
        int maxColumnsQuantity = 0;

        // Найти максимальную длину
        for (Vector v : vectors) {
            maxColumnsQuantity = Math.max(maxColumnsQuantity, v.getSize());
        }

        rows = new Vector[rowsQuantity];

        // Копируем каждый вектор и расширяем до maxColumns
        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(vectors[i]); // копия
            rows[i].resize(maxColumnsQuantity);      // дополняем нулями
        }
    }

    public int getRowsQuantity() {
        return rows.length;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        final int rowsQuantity = rows.length;

        for (int i = 0; i < rowsQuantity; i++) {
            stringBuilder.append(rows[i].toString())
                    .append(i < rowsQuantity - 1 ? ", " : "");
        }

        return stringBuilder.append('}').toString();
    }
}
