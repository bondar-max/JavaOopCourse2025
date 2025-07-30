package ru.java.bondarmax.matrix;

import ru.java.bondarmax.vector.Vector;

public class Matrix {
    private final Vector[] rows;
    private final int rowsCount;
    private final int columnsCount;


    // Конструктор матрицы размера n x m, заполненной нулями
    public Matrix(int n, int m) {
        rowsCount = n;
        this.columnsCount = m;
        rows = new Vector[n];

        for (int i = 0; i < n; i++) {
            rows[i] = new Vector(m);
        }
    }

    // Конструктор копирования
    public Matrix(Matrix other) {
        rowsCount = other.rowsCount;
        this.columnsCount = other.columnsCount;
        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(other.rows[i]);
        }
    }

    // Конструктор из двумерного массива
    public Matrix(double[][] array) {
        rowsCount = array.length;
        this.columnsCount = array[0].length;
        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(array[i]);

            // Дополняем нулями если нужно
            if (rows[i].getSize() < this.columnsCount) {
                double[] temp = new double[this.columnsCount];
                System.arraycopy(array[i], 0, temp, 0, array[i].length);
                rows[i] = new Vector(temp);
            }
        }
    }

    // Конструктор из массива векторов
    public Matrix(Vector[] vectors) {
        int columnsCount = 0;

        for (Vector v : vectors) {
            columnsCount = Math.max(columnsCount, v.getSize());
        }

        rowsCount = vectors.length;
        this.columnsCount = columnsCount;
        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);

            for (int j = 0; j < columnsCount; j++) {
                if (j < vectors[i].getSize()) {
                    rows[i].setComponent(j, vectors[i].getComponent(j));
                }
            }
        }
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return this.columnsCount;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < rowsCount; i++) {
            result.append(rows[i].toString());
            if (i < rowsCount - 1) {
                result.append(", ");
            }
        }
        result.append("}");
        return result.toString();
    }
}
