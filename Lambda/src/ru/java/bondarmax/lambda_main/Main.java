package ru.java.bondarmax.lambda_main;

import ru.java.bondarmax.lambda_person.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Создаем список людей
        List<Person> people = Arrays.asList(
                new Person("Иван", 25),
                new Person("Мария", 30),
                new Person("Петр", 17),
                new Person("Сергей", 42),
                new Person("Анна", 15),
                new Person("Иван", 35),
                new Person("Ольга", 28),
                new Person("Петр", 22),
                new Person("Мария", 19),
                new Person("Алексей", 33)
        );

        System.out.println("Исходный список людей:");

        people.forEach(System.out::println);

        final String NEW_LINE = System.lineSeparator();

        System.out.println(NEW_LINE + "А) Уникальные имена: ");

        people.stream()
                .map(Person::getName)
                .distinct()
                .toList()
                .forEach(System.out::println);
    }
}
