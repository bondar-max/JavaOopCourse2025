package ru.java.bondarmax.shapes;

// Класс для квадрата
public class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }

    @Override
    public String toString() {
        return "Квадрат [стороны=" + getWidth() + "]";
    }
}
