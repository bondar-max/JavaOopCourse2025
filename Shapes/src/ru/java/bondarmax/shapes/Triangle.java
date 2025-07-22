package ru.java.bondarmax.shapes;

// Класс для треугольника
public class Triangle implements Shape {
    private final double width;
    private final double height;
    private final double a, b, c; // стороны треугольника

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        // Вычисляем ширину и высоту
        width = Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
        height = Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);

        // Вычисляем длины сторон
        a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        b = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        c = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        // Формула Герона
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public double getPerimeter() {
        return a + b + c;
    }

    @Override
    public String toString() {
        return "Треугольник [стороны=" + a + ", " + b + ", " + c + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(a);
        hash = prime * hash + Double.hashCode(b);
        hash = prime * hash + Double.hashCode(c);
        hash = prime * hash + Double.hashCode(width);
        hash = prime * hash + Double.hashCode(height);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Double.compare(triangle.a, a) == 0 &&
                Double.compare(triangle.b, b) == 0 &&
                Double.compare(triangle.c, c) == 0;
    }
}
