package ru.java.bondarmax.matrix;

import ru.java.bondarmax.vector.Vector;

public class Matrix {
    private final Vector[] rows;

    /**
     * Конструктор матрицы размера n x m, заполненной нулями
     */
    public Matrix(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными. Передано: rows = " + rows + ", columns = " + columns);
        }

        this.rows = new Vector[rows];

        for (int i = 0; i < rows; i++) {
            this.rows[i] = new Vector(columns);
        }
    }

    /**
     * Конструктор копирования
     */
    public Matrix(Matrix matrix) {
        final int rowsQuantity = matrix.getRowsQuantity();
        rows = new Vector[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    /**
     * Конструктор из двумерного массива
     */
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

    /**
     * Конструктор из массива векторов
     */
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

        // Находим максимальную длину
        for (Vector v : vectors) {
            maxColumnsQuantity = Math.max(maxColumnsQuantity, v.getSize());
        }

        rows = new Vector[rowsQuantity];

        // Копируем каждый вектор и расширяем до maxColumns
        for (int i = 0; i < rowsQuantity; i++) {
            rows[i] = new Vector(vectors[i]); // копия
            rows[i].resize(maxColumnsQuantity); // дополняем нулями
        }
    }

    /**
     * Получение количества строк
     * @return количество строк
     */
    public int getRowsQuantity() {
        return rows.length;
    }

    /**
     * Получение количества столбцов
     * @return количество колонок
     */
    public int getColumnsQuantity() {
        if (rows.length == 0) {
            return 0;
        }

        int maxColumn = 0;

        for (Vector row : rows) {
            if (row.getSize() > maxColumn) {
                maxColumn = row.getSize();
            }
        }

        return maxColumn;
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

    /**
     * Получение вектора-строки по индексу
     * @return вектор-строка
     */
    public Vector getVectorRow(int index) {
        if (index < 0 || index >= getRowsQuantity()) {
            throw new IndexOutOfBoundsException("Индекс строки должен быть [0, " + (getRowsQuantity() - 1) + "]. Передано: " + index);
        }

        return new Vector(rows[index]);
    }

    /**
     * Установка вектора-строки по индексу
     */
    public void setVectorRow(int index, Vector vector) {
        if (index < 0 || index >= getRowsQuantity()) {
            throw new IndexOutOfBoundsException(
                    String.format("Индекс строки должен быть в диапазоне [0, %d]. Передано: %d", getRowsQuantity() - 1, index)
            );
        }

        if (vector == null) {
            throw new NullPointerException("vector не может быть null");
        }

        int maxColumn = getColumnsQuantity();
        double[] components = new double[maxColumn];
        int size = Math.min(vector.getSize(), maxColumn);

        for (int i = 0; i < size; i++) {
            components[i] = vector.getComponent(i);
        }

        rows[index] = new Vector(components);
    }

    /**
     * Получение вектора-столбца по индексу
     * @return выбранный вектор-столбец
     */
    public Vector getVectorColumn(int index) {
        if (index < 0 || index >= getColumnsQuantity()) {
            throw new IndexOutOfBoundsException("Индекс столбца должен быть [0, " + (getColumnsQuantity() - 1) + "]. Передано: " + index);
        }

        int rowsQuantity = getRowsQuantity();
        double[] columns = new double[rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            Vector row = rows[i];
            columns[i] = (index < row.getSize()) ? row.getComponent(index) : 0.0;
        }

        return new Vector(columns);
    }

    /** Транспонирование матрицы
     * @return транспонированная матрица
     */
    public Matrix transpose() {
        int rowsQuantity = getRowsQuantity();
        int columnsQuantity = getColumnsQuantity();

        double[][] transposed = new double[columnsQuantity][rowsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            for (int j = 0; j < columnsQuantity; j++) {
                transposed[j][i] = j < rows[i].getSize() ? rows[i].getComponent(j) : 0.0;
            }
        }

        return new Matrix(transposed);
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
     * @return значение определителя
     */
    public double determinant() {
        int rowsQuantity = getRowsQuantity();
        int columnsQuantity = getColumnsQuantity();

        if (rowsQuantity != columnsQuantity) {
            throw new IllegalArgumentException(
                    "Определитель можно вычислить только для квадратной матрицы. Размер: " + rowsQuantity + "x" + columnsQuantity
            );
        }

        if (rowsQuantity == 0){
            return 1.0;
        }

        if (rowsQuantity == 1){
            return getMatrixElement(0, 0);
        }

        // Создаём копию матрицы
        double[][] matrix = toMatrixArray();

        // Счётчик перестановок строк
        int swapCount = 0;

        // Основной цикл — приводим к верхнетреугольному виду
        for (int pivotColumn = 0; pivotColumn < rowsQuantity; pivotColumn++) {
            // Ищем "ведущий" элемент (не ноль) в текущем столбце, начиная со строки pivotColumn
            int pivotRow = pivotColumn;

            while (pivotRow < rowsQuantity && Math.abs(matrix[pivotRow][pivotColumn]) < 1e-12) {
                pivotRow++;
            }

            // Если не нашли — определитель 0 (матрица вырожденная)
            if (pivotRow == rowsQuantity) {
                return 0.0; // весь столбец нулевой
            }

            // Если ведущая строка не та, что должна быть — меняем местами
            if (pivotRow != pivotColumn) {
                swapMatrixRows(matrix, pivotColumn, pivotRow);
                swapCount++;
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
        if (swapCount % 2 == 1) {
            determinant = -determinant;
        }

        return determinant;
    }

    /**
     * Копирует матрицу в двумерный массив double[][]
     */
    private double[][] toMatrixArray() {
        int rowsQuantity = getRowsQuantity();
        int columnsQuantity = getColumnsQuantity();
        double[][] result = new double[rowsQuantity][columnsQuantity];

        for (int i = 0; i < rowsQuantity; i++) {
            for (int j = 0; j < columnsQuantity; j++) {
                result[i][j] = getMatrixElement(i, j);
            }
        }

        return result;
    }

    /**
     * Меняет местами две строки в массиве
     */
    private void swapMatrixRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    /**
     * Метод для получения элемента матрицы
     */
    private double getMatrixElement(int row, int column) {
        if (row < 0 || row >= rows.length) {
            throw new IndexOutOfBoundsException("Строка " + row + " вне диапазона.");
        }

        if (column >= rows[row].getSize()) {
            return 0.0;
        }

        return rows[row].getComponent(column);
    }
}

