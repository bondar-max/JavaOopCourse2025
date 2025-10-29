package ru.java.bondarmax.lambda;

public record Person(String name, int age) {

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}