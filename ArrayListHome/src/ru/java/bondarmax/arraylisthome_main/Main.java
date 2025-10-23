package ru.java.bondarmax.arraylisthome_main;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final String NEW_LINE = System.lineSeparator();

        // 1. Прочитать в список все строки из файла
        System.out.println("1. Чтение строк из файла 'input.txt':");

        try {
            List<String> lines = readLinesFromPackage();

            if (lines.isEmpty()) {
                System.out.println("Файл существует, но пуст." + NEW_LINE);
            } else {
                System.out.println("Успешно прочитано строк: " + lines.size());
                System.out.println("Содержимое файла: " + lines + NEW_LINE);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ОШИБКА: Файл 'input.txt' не найден в пакете!");
            System.out.println("Убедитесь, что файл находится в: src/ru/java/bondarmax/arraylisthome_main/input.txt");
            System.out.println("Продолжаем выполнение остальных задач..." + NEW_LINE);
        } catch (IOException e) {
            System.out.println("ОШИБКА ввода-вывода: " + e.getMessage() + NEW_LINE);
        }

        // 2. Удалить из списка все четные числа (без создания нового списка)
        System.out.println("2. Удаление четных чисел:");
        List<Integer> numbers1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Исходный список: " + numbers1);
        removeEvenNumbers(numbers1);
        System.out.println("После удаления четных: " + numbers1);
        System.out.println();

        // 3. Создать новый список без повторяющихся элементов
        System.out.println("3. Удаление повторяющихся элементов:");
        List<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 5, 2, 1, 3, 5, 2, 1));
        System.out.println("Исходный список с повторениями: " + numbers2);
        List<Integer> uniqueNumbers = getUniqueNumbers(numbers2);
        System.out.println("Список без повторений: " + uniqueNumbers);
    }

    // 1. Метод для чтения всех строк из файла из пакета
    public static List<String> readLinesFromPackage() throws IOException {
        List<String> lines = new ArrayList<>();

        // Получаем InputStream для файла в пакете
        InputStream inputStream = Main.class.getResourceAsStream("input.txt");

        if (inputStream == null) {
            throw new FileNotFoundException("input.txt не найден в пакете " + Main.class.getPackage().getName());
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    // 2. Метод для удаления четных чисел из списка (без создания нового списка)
    public static void removeEvenNumbers(List<Integer> numbers) {
        numbers.removeIf(number -> number % 2 == 0);
    }

    // 3. Метод для создания нового списка без повторяющихся элементов
    public static List<Integer> getUniqueNumbers(List<Integer> numbers) {
        List<Integer> uniqueNumbers = new ArrayList<>();

        for (Integer currentNumber : numbers) {
            if (!uniqueNumbers.contains(currentNumber)) {
                uniqueNumbers.add(currentNumber);
            }
        }

        return uniqueNumbers;
    }
}