package ru.java.bondarmax.squareroots;

import java.util.Scanner;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Создаем бесконечный поток корней чисел
        DoubleStream squareRootsStream = DoubleStream.iterate(0.0, n -> n + 1)
                .map(Math::sqrt);

        // Читаем количество элементов для вывода
        System.out.print("Введите количество элементов для вычисления: ");
        int elementsCount = scanner.nextInt();

        // Выводим указанное количество элементов
        System.out.println("Первые " + elementsCount + " элементов потока корней:");
        squareRootsStream
                .limit(elementsCount)
                .forEach(element -> System.out.printf("%.4f%n", element));
    }
}
