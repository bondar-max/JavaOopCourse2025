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

        range1.print();

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

        range1.print();

        System.out.println("Введите начало диапазона2:");
        double startNumber2 = scanner.nextDouble();

        System.out.println("Введите конец диапазона2:");
        double endNumber2 = scanner.nextDouble();

        Range range2 = new Range(startNumber2, endNumber2);

        Range intersection = range1.getIntersection(range2);

        if (intersection == null) {
            System.out.printf("Нет пересечения%n");
        } else {
            System.out.printf("%nПересечение%nНачало диапазона: %.2f%n", intersection.getFrom());
            System.out.printf("Конец диапазона: %.2f%n", intersection.getTo());

            System.out.println();
        }

        System.out.println("Объединение");

        Range[] union = range1.getUnion(range2);

        for (int i = 0; i < union.length; i++) {
            System.out.printf("Начало диапазона%d: %.2f%n", i + 1, union[i].getFrom());
            System.out.printf("Конец диапазона%d: %.2f%n", i + 1, union[i].getTo());

            System.out.println();
        }

        System.out.print("Разность");

        Range[] difference = range1.getDifference(range2);

        if (difference.length == 0) {
            System.out.println(" = 0");
            return;
        }

        for (int i = 0; i < difference.length; i++) {
            System.out.printf("%nНачало диапазона%d: %.2f%n", i + 1, difference[i].getFrom());
            System.out.printf("Конец диапазона%d: %.2f", i + 1, difference[i].getTo());

            System.out.println();
        }
    }
}
