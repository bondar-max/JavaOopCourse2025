package ru.java.bondarmax.vector_main;

import ru.java.bondarmax.vector.Vector;

public class Main {
    public static void main(String[] args) {
        // Тестирование конструкторов
        try {
            Vector vector1 = new Vector(4);
            Vector vector2 = new Vector(new double[]{1, 2, 3});
            Vector vector3 = new Vector(5, new double[]{4, 5});
            Vector vector4 = new Vector(vector3);

            System.out.printf("Вектор vector1: %s%nРазмер вектора vector1: %d%n%n", vector1, vector1.getSize());

            System.out.printf("Вектор vector2: %s%nРазмер вектора vector2: %d%n%n", vector2, vector2.getSize());

            System.out.printf("Вектор vector3: %s%nРазмер вектора vector3: %d%n%n", vector3, vector3.getSize());

            System.out.printf("Вектор vector4: %s%nРазмер вектора vector4: %d%n%n", vector4, vector4.getSize());

            vector4.setComponent(2, 6);
            System.out.printf("Вектор vector4 после установки по индексу: %s%n%n", vector4);

            System.out.printf("Компонент 1 вектора vector3: : %s%n%n", vector3.getComponent(1));

            vector4.subtract(vector3);
            System.out.printf("Вектор vector4 после вычитания vector3: %s%n%n", vector4);

            vector1.add(vector2);
            System.out.printf("Вектор vector1 после добавления vector2: %s%n%n", vector1);

            Vector sum = Vector.getSum(vector1, vector3);
            System.out.printf("Сумма vector1 и vector3: %s%n%n", sum);

            System.out.printf("Скалярное произведение vector1 и vector2: %.2f%n%n", Vector.getDotProduct(vector1, vector2));

            System.out.printf("Длина вектора vector2: %.2f%n%n", vector2.getLength());

            vector2.reverse();
            System.out.printf("Вектор vector2 после разворота: %s%n%n", vector2);

            Vector difference = Vector.getDifference(vector3, vector1);
            System.out.println("Разность vector3 и vector1: " + difference);

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
