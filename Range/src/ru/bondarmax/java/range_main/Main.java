package ru.bondarmax.java.range_main;

import ru.bondarmax.java.range.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите начало диапазона1:");
        double startNumber = scanner.nextDouble();

        System.out.println("Введите конец диапазона1:");
        double endNumber = scanner.nextDouble();

        Range range = new Range(startNumber, endNumber);

        range.printInformation();

        System.out.println("Введите число и узнайте принадлежит ли оно диапазону:");
        double number = scanner.nextDouble();

        if (range.isInside(number)) {
            System.out.println("Число внутри диапазона");
        } else {
            System.out.println("Число вне диапазона");
        }

        System.out.printf("%nИзмените начало диапазона1:%n");
        range.setFrom(scanner.nextDouble());

        System.out.println("Измените конец диапазона1:");
        range.setTo(scanner.nextDouble());

        range.printInformation();

        System.out.println("Введите начало диапазона2:");
        startNumber = scanner.nextDouble();

        System.out.println("Введите конец диапазона2:");
        endNumber = scanner.nextDouble();

        Range range2 = new Range(startNumber, endNumber);

        System.out.printf("%nПересечение%n");

        Range intersectionResult = range.getSetsIntersection(range2);

        if (intersectionResult != null) {
            System.out.printf("Начало диапазона: %.2f%n", intersectionResult.getFrom());
            System.out.printf("Конец диапазона: %.2f%n", intersectionResult.getTo());

            System.out.println();
        } else {
            System.out.println("Нет пересечения");
        }

        System.out.println("Объединение");

        Range[] unionResult = range.getSetsUnion(range2);

        for (int i = 0; i < unionResult.length; i++) {
            System.out.printf("Начало диапазона%d: %.2f%n", i + 1, unionResult[i].getFrom());
            System.out.printf("Конец диапазона%d: %.2f%n", i + 1, unionResult[i].getTo());

            System.out.println();
        }

        System.out.println("Разность");

        Range[] differenceResult = range.getSetsDifference(range2);

        for (int i = 0; i < differenceResult.length; i++) {
            System.out.printf("Начало диапазона%d: %.2f%n", i + 1, differenceResult[i].getFrom());
            System.out.printf("Конец диапазона%d: %.2f%n", i + 1, differenceResult[i].getTo());

            System.out.println();
        }
    }
}
