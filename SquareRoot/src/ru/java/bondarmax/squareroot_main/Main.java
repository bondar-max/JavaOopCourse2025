package ru.java.bondarmax.squareroot_main;

import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Создаем бесконечный поток корней чисел
        Stream<Double> squareRootStream = Stream.iterate(1.0, n -> n + 1)
                .map(Math::sqrt);

        // Читаем количество элементов для вывода
        System.out.print("Введите количество элементов для вычисления: ");
        int count = scanner.nextInt();

        // Выводим указанное количество элементов
        System.out.println("Первые " + count + " элементов потока корней:");
        squareRootStream
                .limit(count)
                .forEach(element -> System.out.printf("%.4f%n", element));

        scanner.close();
    }
}
