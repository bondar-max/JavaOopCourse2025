package ru.java.bondarmax.range_main;

import ru.java.bondarmax.range.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите начало диапазона1:");
        double startNumber1 = scanner.nextDouble();

        System.out.println("Введите конец диапазона1:");
        double endNumber1 = scanner.nextDouble();

        Range range1 = new Range(startNumber1, endNumber1);

        range1.printInformation();

        System.out.println("Введите число и узнайте принадлежит ли оно диапазону:");
        double number = scanner.nextDouble();

        if (range1.isInside(number)) {
            System.out.println("Число внутри диапазона");
        } else {
            System.out.println("Число вне диапазона");
        }

        System.out.printf("%nИзмените начало диапазона1:%n");
        range1.setFrom(scanner.nextDouble());

        System.out.println("Измените конец диапазона1:");
        range1.setTo(scanner.nextDouble());

        range1.printInformation();

        System.out.println("Введите начало диапазона2:");
        double startNumber2 = scanner.nextDouble();

        System.out.println("Введите конец диапазона2:");
        double endNumber2 = scanner.nextDouble();

        Range range2 = new Range(startNumber2, endNumber2);

        System.out.printf("%nПересечение%n");

        Range intersectionResult = range1.getIntersection(range2);

        if (intersectionResult != null) {
            System.out.printf("Начало диапазона: %.2f%n", intersectionResult.getFrom());
            System.out.printf("Конец диапазона: %.2f%n", intersectionResult.getTo());

            System.out.println();
        } else {
            System.out.println("Нет пересечения\n");
        }

        System.out.println("Объединение");

        Range[] unionResult = range1.getUnion(range2);

        for (int i = 0; i < unionResult.length; i++) {
            System.out.printf("Начало диапазона%d: %.2f%n", i + 1, unionResult[i].getFrom());
            System.out.printf("Конец диапазона%d: %.2f%n", i + 1, unionResult[i].getTo());

            System.out.println();
        }

        System.out.println("Разность");

        Range[] differenceResult = range1.getDifference(range2);

        for (int i = 0; i < differenceResult.length; i++) {
            System.out.printf("Начало диапазона%d: %.2f%n", i + 1, differenceResult[i].getFrom());
            System.out.printf("Конец диапазона%d: %.2f%n", i + 1, differenceResult[i].getTo());

            System.out.println();
        }
    }
}
