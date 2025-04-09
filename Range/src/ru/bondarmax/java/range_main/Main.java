package ru.bondarmax.java.range_main;

import ru.bondarmax.java.range.Range;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       /* Scanner scanner = new Scanner(System.in);

        System.out.println("Введите начало диапазона:");
        double startNumber = scanner.nextDouble();

        System.out.println("Введите конец диапазона:");
        double endNumber = scanner.nextDouble();

        Range range = new Range(startNumber, endNumber);

        System.out.printf("Начало диапазона: %.2f%n", range.getFrom());
        System.out.printf("Конец диапазона: %.2f%n", range.getTo());
        System.out.printf("Длина диапазона: %.2f%n", range.getLength());

        System.out.println("Введите число и узнайте принадлежит ли оно диапазону:");
        double number = scanner.nextDouble();

        if (range.isInside(number)) {
            System.out.println("Число внутри диапазона");
        } else {
            System.out.println("Число вне диапазона");
        }*/

        double startNumber = 2;
        double endNumber = 7;

        Range range = new Range(4, 10);

        Range range2 = new Range(2, 4);

        Range range3 = range.getSetsIntersection(range2);

        if(range3!=null){
            System.out.printf("Начало диапазона: %.2f%n", range3.getFrom());
            System.out.printf("Конец диапазона: %.2f%n", range3.getTo());
        }
        else{
            System.out.println("Нет пересечения");
        }



    }
}
