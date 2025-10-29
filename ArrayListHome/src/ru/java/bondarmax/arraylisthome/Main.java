package ru.java.bondarmax.arraylisthome;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. Прочитать в список все строки из файла
        System.out.println("1. Чтение строк из файла 'input.txt':");

        try {
            List<String> lines = readLinesFromPackage();

            if (lines.isEmpty()) {
                System.out.println("Файл существует, но пуст.");
            } else {
                System.out.println("Успешно прочитано строк: " + lines.size());
                System.out.println("Содержимое файла: " + lines);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ОШИБКА: Файл 'input.txt' не найден в пакете!");
            System.out.println("Убедитесь, что файл находится в: src/ru/java/bondarmax/arraylisthome/input.txt");
            System.out.println("Продолжаем выполнение остальных задач...");
        } catch (IOException e) {
            System.out.println("ОШИБКА ввода-вывода: " + e.getMessage());
        }

        System.out.println();

        // 2. Удалить из списка все четные числа (без создания нового списка)
        System.out.println("2. Удаление четных чисел:");

        List<Integer> numbers1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Исходный список: " + numbers1);
        removeEvenNumbersWithRemoveIf(numbers1);
        System.out.println("После removeIf: " + numbers1);

        List<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        removeEvenNumbersManual(numbers2);
        System.out.println("После ручного удаления: " + numbers2);
        System.out.println();

        // 3. Создать новый список без повторяющихся элементов
        System.out.println("3. Удаление повторяющихся элементов:");
        List<Integer> numbers3 = new ArrayList<>(Arrays.asList(1, 5, 2, 1, 3, 5, 2, 1));
        System.out.println("Исходный список с повторениями: " + numbers3);
        List<Integer> uniqueNumbers = getUniqueElements(numbers3);
        System.out.println("Список без повторений: " + uniqueNumbers);
    }

    // 1. Метод для чтения всех строк из файла из пакета
    public static List<String> readLinesFromPackage() throws IOException {
        // Получаем InputStream для файла в пакете
        InputStream inputStream = Main.class.getResourceAsStream("input.txt");

        if (inputStream == null) {
            throw new FileNotFoundException("input.txt не найден в пакете " + Main.class.getPackage().getName());
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    // 2. Метод для удаления четных чисел из списка (без создания нового списка)
    // 2.1 Метод с removeIf
    public static void removeEvenNumbersWithRemoveIf(List<Integer> numbers) {
        numbers.removeIf(number -> number % 2 == 0);
    }

    // 2.2 Метод без removeIf и итератора
    public static void removeEvenNumbersManual(List<Integer> numbers) {
        for (int i = numbers.size() - 1; i >= 0; i--) {
            if (numbers.get(i) % 2 == 0) {
                numbers.remove(i);
            }
        }
    }

    // 3. Метод для создания нового списка без повторяющихся элементов
    public static <T> List<T> getUniqueElements(List<T> list) {
        List<T> uniqueElements = new ArrayList<>(list.size());

        for (T currentElement : list) {
            if (!uniqueElements.contains(currentElement)) {
                uniqueElements.add(currentElement);
            }
        }

        return uniqueElements;
    }
}