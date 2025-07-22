package ru.java.bondarmax.shapes_main;

import java.util.Arrays;
import java.util.Comparator;
import ru.java.bondarmax.shapes.*;

public class Main {
    public static void main(String[] args) {
        // Создаем массив фигур
        Shape[] shapes = {
                new Rectangle(5, 10),
                new Circle(7),
                new Triangle(3, 1, 5, 1, 8, 10),
                new Rectangle(8, 8),
                new Circle(4),
                new Triangle(3, 1, 5, 1, 8, 10),
                new Rectangle(12, 3),
                new Circle(2),
                new Triangle(1, 1, 12, 1, 8, 10),
                new Square(8),
                new Rectangle(7, 7)
        };

        // Находим фигуру с максимальной площадью
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::getArea));
        Shape maxAreaShape = shapes[shapes.length - 1];
        System.out.println("Фигура с максимальной площадью: " + maxAreaShape);
        System.out.println("Площадь: " + maxAreaShape.getArea());

        // Находим фигуру со вторым по величине периметром
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::getPerimeter));
        Shape secondMaxPerimeterShape = shapes[shapes.length - 2];
        System.out.println("\nФигура со вторым по величине периметром: " + secondMaxPerimeterShape);
        System.out.println("Периметр: " + secondMaxPerimeterShape.getPerimeter());
    }
}
