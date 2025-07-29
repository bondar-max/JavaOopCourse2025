package ru.java.bondarmax.matrix;

import ru.java.bondarmax.vector.Vector;

public class Matrix {
    private final Vector[] rows;
    private final int rowsCount;
    private final int columnsCount;


    // Конструкторы
    public Matrix(int n, int m) {
        rowsCount = n;
        columnsCount = m;
        rows = new Vector[n];

        for (int i = 0; i < n; i++) {
            rows[i] = new Vector(m);
        }
    }

    public Matrix(Matrix other) {
        rowsCount = other.rowsCount;
        columnsCount = other.columnsCount;
        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(other.rows[i]);
        }
    }

    public Matrix(double[][] array) {
        rowsCount = array.length;
        columnsCount = array[0].length;
        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(array[i]);

            // Дополняем нулями если нужно
            if (rows[i].getSize() < columnsCount) {
                double[] temp = new double[columnsCount];
                System.arraycopy(array[i], 0, temp, 0, array[i].length);
                rows[i] = new Vector(temp);
            }
        }
    }

    public Matrix(Vector[] vectors) {
        rowsCount = vectors.length;
        columnsCount = vectors[0].getSize();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Определяем максимальную ширину числа для выравнивания
        int maxWidth = getMaxWidth();

        sb.append(System.lineSeparator());

        for (int i = 0; i < rowsCount; i++) {
            sb.append("  ");

            for (int j = 0; j < columnsCount; j++) {
                String value = String.valueOf(rows[i].getComponent(j));

                // Добавляем пробелы для выравнивания
                sb.append(value);
                sb.append(" ".repeat(maxWidth - value.length()));

                if (j < columnsCount - 1) {
                    sb.append("  ");  // добавляем пробелы между элементами
                }
            }

            sb.append(System.lineSeparator());  // используем универсальный перенос строки
        }

        return sb.toString();
    }

    private int getMaxWidth() {
        int max = 0;

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                String value = String.valueOf(rows[i].getComponent(j));

                if (value.length() > max) {
                    max = value.length();
                }
            }
        }

        return max + 2;  // добавляем 2 для отступов
    }
}
