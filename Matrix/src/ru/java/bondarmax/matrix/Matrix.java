package ru.java.bondarmax.matrix;

import ru.java.bondarmax.vector.Vector;

public class Matrix {
    private Vector[] rows;

    /**
     * Конструктор матрицы размера n x m, заполненной нулями
     */
    public Matrix(int rowsQuantity, int columnsQuantity) {
        if (rowsQuantity <= 0 || columnsQuantity <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть больше нуля. Передано: rowsQuantity = " + rowsQuantity + ", columnsQuantity = " + columnsQuantity);
        }

        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(columnsQuantity);
        }
    }

    /**
     * Конструктор копирования
     */
    public Matrix(Matrix matrix) {
        int rowsQuantity = matrix.getRowsQuantity();
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    /**
     * Конструктор из двумерного массива
     */
    public Matrix(double[][] array) {
        if (array == null) {
            throw new NullPointerException("Array не может быть null");
        }

        if (array.length == 0) {
            throw new IllegalArgumentException("Array не может быть пустым. Длина = 0");
        }

        int maxColumnsQuantity = 0;

        for (double[] row : array) {
            if (row == null) {
                throw new NullPointerException("Array[row] не может быть null");
            }

            maxColumnsQuantity = Math.max(maxColumnsQuantity, row.length);
        }

        if (maxColumnsQuantity == 0) {
            throw new IllegalArgumentException("Все строки пустые — матрица не может быть создана. Длина всех строк = 0");
        }

        int rowsQuantity = array.length;
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(maxColumnsQuantity, array[i]);
        }
    }

    /**
     * Конструктор из массива векторов
     */
    public Matrix(Vector[] sourceVectors) {
        if (sourceVectors == null) {
            throw new NullPointerException("SourceVectors не может быть null");
        }

        if (sourceVectors.length == 0) {
            throw new IllegalArgumentException("SourceVectors не может быть пустым. Длина = 0");
        }

        int maxColumnsQuantity = 0;

        for (Vector vector : sourceVectors) {
            if (vector == null) {
                throw new NullPointerException("Вектор в массиве sourceVectors не может быть null");
            }

            maxColumnsQuantity = Math.max(maxColumnsQuantity, vector.getSize());
        }

        int rowsQuantity = sourceVectors.length;
        // создаём массив строк
        rows = new Vector[rowsQuantity];

        for (int rowIndex = 0; rowIndex < rowsQuantity; rowIndex++) {
            Vector sourceVector = sourceVectors[rowIndex]; // кэшируем исходный вектор
            int sourceVectorSize = sourceVector.getSize();

            // Создаём новый вектор нужной длины
            Vector newVector = new Vector(maxColumnsQuantity);

            // Копируем компоненты
            for (int columnIndex = 0; columnIndex < sourceVectorSize; columnIndex++) {
                newVector.setComponent(columnIndex, sourceVector.getComponent(columnIndex));
            }

            rows[rowIndex] = newVector;
        }
    }

    /**
     * Получение количества строк
     */
    public int getRowsQuantity() {
        return rows.length;
    }

    /**
     * Получение количества столбцов
     */
    public int getColumnsQuantity() {
        return rows[0].getSize();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        int rowsQuantity = rows.length;

        stringBuilder.append(rows[0]);

        for (int i = 1; i < rowsQuantity; i++) {
            stringBuilder.append(", ").append(rows[i]);
        }

        return stringBuilder.append('}').toString();
    }

    /**
     * Получение вектора-строки по индексу
     */
    public Vector getRow(int index) {
        if (index < 0 || index >= getRowsQuantity()) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", getRowsQuantity() - 1, index));
        }

        return new Vector(rows[index]);
    }

    /**
     * Установка вектора-строки по индексу
     */
    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= getRowsQuantity()) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", getRowsQuantity() - 1, index));
        }

        if (vector == null) {
            throw new NullPointerException("Vector не может быть null");
        }

        int expectedSize = getColumnsQuantity();

        if (vector.getSize() != expectedSize) {
            throw new IllegalArgumentException(String.format("Размер вектора должен быть %d. Текущий размер: %d", expectedSize, vector.getSize()));
        }

        rows[index] = new Vector(vector);
    }

    /**
     * Получение вектора-столбца по индексу
     */
    public Vector getColumn(int index) {
        if (index < 0 || index >= getColumnsQuantity()) {
            throw new IndexOutOfBoundsException(String.format("Index должен быть в диапазоне [0, %d]. Переданное значение: index = %d", getColumnsQuantity() - 1, index));
        }

        int rowsQuantity = getRowsQuantity();
        double[] column = new double[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            column[i] = rows[i].getComponent(index);
        }

        return new Vector(column);
    }

    /**
     * Транспонирование матрицы
     */
    public void transpose() {
        int columnsQuantity = getColumnsQuantity();
        Vector[] transposedMatrixRows = new Vector[columnsQuantity];

        for (int newRowIndex = 0; newRowIndex < columnsQuantity; newRowIndex++) {
            // Получаем столбец из исходной матрицы и используем его как строку в транспонированной
            transposedMatrixRows[newRowIndex] = getColumn(newRowIndex);
        }

        rows = transposedMatrixRows;
    }

    /**
     * Умножение матрицы на скаляр
     */
    public void multiply(double scalar) {
        for (Vector row : rows) {
            row.multiply(scalar);
        }
    }

    /**
     * Вычисляет определитель квадратной матрицы методом Гаусса (приведение к ступенчатому виду).
     */
    public double calculateDeterminant() {
        int rowsQuantity = getRowsQuantity();
        int columnsQuantity = getColumnsQuantity();

        if (rowsQuantity != columnsQuantity) {
            throw new IllegalStateException("Определитель можно вычислить только для квадратной матрицы. Размер: " + rowsQuantity + "x" + columnsQuantity);
        }

        if (rowsQuantity == 1) {
            return rows[0].getComponent(0);
        }

        final double EPSILON = 1e-12;

        // Создаём копию матрицы
        double[][] matrix = createMatrixArray();

        // Счётчик перестановок строк
        int swapsCount = 0;

        // Основной цикл — приводим к верхне-треугольному виду
        for (int pivotColumn = 0; pivotColumn < rowsQuantity; pivotColumn++) {
            // Ищем "ведущий" элемент (не ноль) в текущем столбце, начиная со строки pivotColumn
            int pivotRow = pivotColumn;

            while (pivotRow < rowsQuantity && Math.abs(matrix[pivotRow][pivotColumn]) < EPSILON) {
                pivotRow++;
            }

            // Если не нашли — определитель 0 (матрица вырожденная)
            if (pivotRow == rowsQuantity) {
                return 0.0; // весь столбец нулевой
            }

            // Если ведущая строка не та, что должна быть — меняем местами
            if (pivotRow != pivotColumn) {
                swapMatrixRows(matrix, pivotColumn, pivotRow);
                swapsCount++;
            }

            // Обрабатываем каждую строку ниже ведущей, чтобы обнулить элемент в текущем столбце
            for (int lowerRow = pivotColumn + 1; lowerRow < rowsQuantity; lowerRow++) {
                // Коэффициент, чтобы обнулить matrix[lowerRow][pivotColumn]
                double multiplier = matrix[lowerRow][pivotColumn] / matrix[pivotColumn][pivotColumn];

                // Проходим по каждому столбцу колонки lowerRow, начиная с pivotColumn
                for (int currentColumn = pivotColumn; currentColumn < rowsQuantity; currentColumn++) {
                    matrix[lowerRow][currentColumn] -= multiplier * matrix[pivotColumn][currentColumn];
                }
            }
        }

        // Определитель = произведение диагональных элементов
        double determinant = 1.0;

        for (int i = 0; i < rowsQuantity; i++) {
            determinant *= matrix[i][i];
        }

        // Учитываем перестановки строк: каждая меняет знак
        if (swapsCount % 2 == 1) {
            determinant = -determinant;
        }

        return determinant;
    }

    /**
     * Копирует матрицу в двумерный массив double[][]
     */
    private double[][] createMatrixArray() {
        int rowsQuantity = getRowsQuantity();
        int columnsQuantity = getColumnsQuantity();
        double[][] result = new double[rowsQuantity][columnsQuantity];

        for (int rowIndex = 0; rowIndex < rowsQuantity; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnsQuantity; columnIndex++) {
                result[rowIndex][columnIndex] = rows[rowIndex].getComponent(columnIndex);
            }
        }

        return result;
    }

    /**
     * Меняет местами две строки в массиве
     */
    private static void swapMatrixRows(double[][] matrix, int row1Index, int row2Index) {
        double[] temp = matrix[row1Index];
        matrix[row1Index] = matrix[row2Index];
        matrix[row2Index] = temp;
    }

    /**
     * Метод для получения компонента матрицы
     */
    public double getComponent(int row, int column) {
        if (row < 0 || row >= rows.length) {
            throw new IndexOutOfBoundsException(String.format("Индекс строки должен быть в диапазоне [0, %d]. Переданное значение: index = %d", rows.length - 1, row));
        }

        if (column < 0 || column >= getColumnsQuantity()) {
            throw new IndexOutOfBoundsException(String.format("Индекс столбца должен быть в диапазоне [0, %d]. Передано: %d", getColumnsQuantity() - 1, column));
        }

        return rows[row].getComponent(column);
    }

    /**
     * Умножает эту матрицу на заданный вектор.
     */
    public Vector multiply(Vector vector) {
        if (vector == null) {
            throw new NullPointerException("Vector не может быть null");
        }

        int columnsQuantity = getColumnsQuantity();

        if (vector.getSize() != columnsQuantity) {
            throw new IllegalArgumentException("Размер вектора должен быть равен числу столбцов матрицы: " + columnsQuantity + ", но передан вектор размером " + vector.getSize());
        }

        int rowsQuantity = getRowsQuantity();
        double[] resultComponents = new double[rowsQuantity];

        for (int rowIndex = 0; rowIndex < rowsQuantity; rowIndex++) {
            resultComponents[rowIndex] = Vector.getProduct(rows[rowIndex], vector);
        }

        return new Vector(resultComponents);
    }

    /**
     * Складывает данную матрицу с другой.
     */
    public void add(Matrix matrix) {
        validateSameDimensions(matrix, "Сложение");

        for (int i = 0; i < getRowsQuantity(); i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    /**
     * Вычитает из данной матрицы другую.
     */
    public void subtract(Matrix matrix) {
        validateSameDimensions(matrix, "Вычитание");

        for (int i = 0; i < getRowsQuantity(); i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    /**
     * Проверяет совпадение размеров с другой матрицей.
     */
    private void validateSameDimensions(Matrix matrix, String operation) {
        if (matrix == null) {
            throw new NullPointerException("Matrix не может быть null");
        }

        if (getRowsQuantity() != matrix.getRowsQuantity() || getColumnsQuantity() != matrix.getColumnsQuantity()) {
            throw new IllegalArgumentException(operation + ": размеры матриц не совпадают: " + getRowsQuantity() + "x" + getColumnsQuantity() + " и " + matrix.getRowsQuantity() + "x" + matrix.getColumnsQuantity());
        }
    }

    /**
     * Возвращает сумму двух матриц.
     */
    public static Matrix getSum(Matrix leftMatrix, Matrix rightMatrix) {
        validateNotNull(leftMatrix, rightMatrix, "Сумма");

        Matrix result = new Matrix(leftMatrix);
        result.add(rightMatrix);

        return result;
    }

    /**
     * Возвращает разность двух матриц.
     */
    public static Matrix getDifference(Matrix leftMatrix, Matrix rightMatrix) {
        validateNotNull(leftMatrix, rightMatrix, "Разность");

        Matrix result = new Matrix(leftMatrix);
        result.subtract(rightMatrix);

        return result;
    }

    /**
     * Проверяет, что обе матрицы не null.
     */
    private static void validateNotNull(Matrix leftMatrix, Matrix rightMatrix, String operation) {
        if (leftMatrix == null) {
            throw new NullPointerException(operation + ": левая матрица не может быть null");
        }

        if (rightMatrix == null) {
            throw new NullPointerException(operation + ": правая матрица не может быть null");
        }
    }

    /**
     * Умножает две матрицы: result = left × right.(Число столбцов левой матрицы должно равняться числу строк правой)
     * левая матрица (m × n), правая матрица (n × p), новая матрица размером m × p — произведение
     */
    public static Matrix getProduct(Matrix leftMatrix, Matrix rightMatrix) {
        validateNotNull(leftMatrix, rightMatrix, "Умножение");

        int commonDimension = leftMatrix.getColumnsQuantity(); // столбцы левой = строки правой

        if (commonDimension != rightMatrix.getRowsQuantity()) {
            throw new IllegalArgumentException("Несовместимые размеры: левая матрица имеет " + commonDimension + " столбцов, а правая — " + rightMatrix.getRowsQuantity() + " строк");
        }

        int resultRowsQuantity = leftMatrix.getRowsQuantity();
        int resultColumnsQuantity = rightMatrix.getColumnsQuantity();

        // Создаём массив для результата
        double[][] multiplicationResult = new double[resultRowsQuantity][resultColumnsQuantity];

        // Основной цикл: result[i][j] = Σ left[i][k] * right[k][j]
        for (int rowIndex = 0; rowIndex < resultRowsQuantity; rowIndex++) {
            Vector leftRow = leftMatrix.rows[rowIndex];

            for (int columnIndex = 0; columnIndex < resultColumnsQuantity; columnIndex++) {
                double sum = 0.0;

                for (int commonDimensionIndex = 0; commonDimensionIndex < commonDimension; commonDimensionIndex++) {
                    sum += leftRow.getComponent(commonDimensionIndex) * rightMatrix.rows[commonDimensionIndex].getComponent(columnIndex);
                }

                multiplicationResult[rowIndex][columnIndex] = sum;
            }
        }

        return new Matrix(multiplicationResult);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Matrix matrix = (Matrix) obj;

        if (getRowsQuantity() != matrix.getRowsQuantity() || getColumnsQuantity() != matrix.getColumnsQuantity()) {
            return false;
        }

        for (int i = 0; i < getRowsQuantity(); i++) {
            if (!rows[i].equals(matrix.rows[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int hash = 1;

        for (Vector row : rows) {
            hash = PRIME * hash + row.hashCode();
        }

        return hash;
    }
}