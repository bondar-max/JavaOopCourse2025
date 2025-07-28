package ru.java.bondarmax.comparators;

import ru.java.bondarmax.shapes.Shape;

import java.util.Comparator;

// Компаратор для сравнения по площади
public class AreaComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape s1, Shape s2) {
        if (s1.getPerimeter() > s2.getPerimeter()) {
            return 1;
        } else if (s1.getPerimeter() < s2.getPerimeter()) {
            return -1;
        }

        return 0;
    }
}
