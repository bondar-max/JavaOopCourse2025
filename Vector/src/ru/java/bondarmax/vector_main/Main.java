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

            System.out.println("v1: " + v1);
            System.out.println("размер вектора v1: " + v1.getSize());
            System.out.println();

            System.out.println("v2: " + v2);
            System.out.println("размер вектора v2: " + v2.getSize());
            System.out.println();

            System.out.println("v3: " + v3);
            System.out.println("размер вектора v3: " + v3.getSize());
            System.out.println();

            System.out.println("v4: " + v4);
            System.out.println("размер вектора v4: " + v4.getSize());
            System.out.println();

            v4.setComponent(2,6);
            System.out.println("v4 после установки по индексу: " + v4);
            System.out.println();

            System.out.println("компонент 1 вектора v3: " + v2.getComponent(1));
            System.out.println();

            v4.subtract(v3);
            System.out.println("v4 после вычитания v3: " + v4);
            System.out.println();

            v1.add(v2);
            System.out.println("v1 после добавления v2: " + v1);
            System.out.println();

            Vector sum = Vector.add(v1, v3);
            System.out.println("Сумма v1 и v3: " + sum);
            System.out.println();

            System.out.println("Скалярное произведение v1 и v2: " + Vector.dotProduct(v1, v2));
            System.out.println();

            System.out.println("Длина v2: " + v2.length());
            System.out.println();

            v2.reverse();
            System.out.println("v2 после разворота: " + v2);
            System.out.println();

            Vector diff = Vector.subtract(v3, v1);
            System.out.println("Разность v3 и v1: " + diff);

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }


    }
}
