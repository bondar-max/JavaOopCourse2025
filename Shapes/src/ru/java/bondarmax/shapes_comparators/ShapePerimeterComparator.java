package ru.java.bondarmax.shapes_comparators;

import ru.java.bondarmax.shapes.Shape;

import java.util.Comparator;

// Компаратор для сравнения по периметру
public class ShapePerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        if (shape1 == null) {
            return shape2 == null ? 0 : -1;
        }
        
        if (shape2 == null) {
            return 1;
        }

        return Double.compare(shape1.getPerimeter(), shape2.getPerimeter());
    }
}