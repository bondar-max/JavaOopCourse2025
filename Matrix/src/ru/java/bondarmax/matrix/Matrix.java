package ru.java.bondarmax.matrix;

import ru.java.bondarmax.vector.Vector;

public class Matrix {
    private final Vector[] rows;
    private final int rowsQuantity;
    private final int columnsQuantity;


    // Конструктор матрицы размера n x m, заполненной нулями
    public Matrix(int n, int m) {
        rowsQuantity = n;
        columnsQuantity = m;
        rows = new Vector[n];

        for (int i = 0; i < n; i++) {
            rows[i] = new Vector(m);
        }
    }

    // Конструктор копирования
    public Matrix(Matrix other) {
        rowsQuantity = other.rowsQuantity;
        columnsQuantity = other.columnsQuantity;
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(other.rows[i]);
        }
    }

    // Конструктор из двумерного массива
    public Matrix(double[][] array) {
        rowsQuantity = array.length;
        columnsQuantity = array[0].length;
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(array[i]);

            // Дополняем нулями если нужно
            if (rows[i].getSize() < columnsQuantity) {
                double[] temp = new double[columnsQuantity];
                System.arraycopy(array[i], 0, temp, 0, array[i].length);
                rows[i] = new Vector(temp);
            }
        }
    }

    // Конструктор из массива векторов
    public Matrix(Vector[] vectors) {
        int  largestColumn = 0;

        for (Vector v : vectors) {
            largestColumn = Math.max(largestColumn, v.getSize());
        }

        rowsQuantity = vectors.length;
        columnsQuantity = largestColumn;
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(columnsQuantity);

            for (int j = 0; j < columnsQuantity; j++) {
                if (j < vectors[i].getSize()) {
                    rows[i].setComponent(j, vectors[i].getComponent(j));
                }
            }
        }
    }

    public int getRowsQuantity() {
        return rowsQuantity;
    }

    public int getColumnsQuantity() {
        return columnsQuantity;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < rowsQuantity; i++) {
            result.append(rows[i].toString());
            if (i < rowsQuantity - 1) {
                result.append(", ");
            }
        }
        result.append("}");
        return result.toString();
    }
}
