package ru.java.bondarmax.shapes_main;

import java.util.Arrays;

import ru.java.bondarmax.comparators.*;
import ru.java.bondarmax.shapes.*;

public class Main {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(3, 5);
        Circle circle = new Circle(3);
        Triangle triangle = new Triangle(3, 1, 5, 1, 8, 10);
        Square square = new Square(8);

        square.setSide(8);
        System.out.println("Сторона квадрата: " + square.getSide());

        circle.setRadius(2);
        System.out.println("Радиус круга: " + circle.getRadius());

        rectangle.setHeight(3);
        rectangle.setWidth(4);
        System.out.println("Высота прямоугольника: " + rectangle.getHeight());
        System.out.println("Ширина прямоугольника: " + rectangle.getWidth());

        triangle.setX1(1);
        triangle.setY1(1);
        System.out.println("Координата треугольника 1 (x1, y1): " + triangle.getX1() + " " + triangle.getY1());

        triangle.setX2(3);
        triangle.setY2(4);
        System.out.println("Координата треугольника 2 (x2, y2): " + triangle.getX2() + " " + triangle.getY2());

        triangle.setX3(2);
        triangle.setY3(8);
        System.out.println("Координата треугольника 3 (x3, y3): " + triangle.getX3() + " " + triangle.getY3());
        System.out.println();

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
        Arrays.sort(shapes, new AreaComparator());
        Shape maxAreaShape = shapes[shapes.length - 1];
        System.out.println("Фигура с максимальной площадью: " + maxAreaShape);
        System.out.println("Площадь: " + maxAreaShape.getArea());
        System.out.println();

        // Находим фигуру со вторым по величине периметром
        Arrays.sort(shapes, new PerimeterComparator());
        Shape secondMaxPerimeterShape = shapes[shapes.length - 2];
        System.out.println("Фигура со вторым по величине периметром: " + secondMaxPerimeterShape);
        System.out.println("Периметр: " + secondMaxPerimeterShape.getPerimeter());
    }
}