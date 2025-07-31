package ru.java.bondarmax.vector_main;

import ru.java.bondarmax.vector.Vector;

public class Main {
    public static void main(String[] args) {
        // Тестирование конструкторов
        try {
            Vector v1 = new Vector(4);
            Vector v2 = new Vector(new double[]{1, 2, 3});
            Vector v3 = new Vector(5, new double[]{4, 5});
            Vector v4 = new Vector(v3);

            System.out.printf("Вектор v1: %s%nРазмер вектора v1: %d%n%n", v1, v1.getSize());

            System.out.printf("Вектор v2: %s%nРазмер вектора v2: %d%n%n", v2, v2.getSize());

            System.out.printf("Вектор v3: %s%nРазмер вектора v3: %d%n%n", v3, v3.getSize());

            System.out.printf("Вектор v4: %s%nРазмер вектора v4: %d%n%n", v4, v4.getSize());

            v4.setComponent(2, 6);
            System.out.printf("Вектор v4 после установки по индексу: %s%n%n", v4);

            System.out.printf("Компонент 1 вектора v3: : %s%n%n", v3.getComponent(1));

            v4.subtract(v3);
            System.out.printf("Вектор v4 после вычитания v3: %s%n%n", v4);

            v1.add(v2);
            System.out.printf("Вектор v1 после добавления v2: %s%n%n", v1);

            Vector sum = Vector.add(v1, v3);
            System.out.printf("Сумма v1 и v3: %s%n%n", sum);

            double dotProduct = Vector.dotProduct(v1, v2);
            System.out.printf("Скалярное произведение v1 и v2: %.2f%n%n", dotProduct);

            System.out.printf("Длина вектора v2: %.2f%n%n", v2.getLength());

            v2.reverse();
            System.out.printf("Вектор v2 после разворота: %s%n%n", v2);

            Vector diff = Vector.subtract(v3, v1);
            System.out.println("Разность v3 и v1: " + diff);

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }


    }
}
