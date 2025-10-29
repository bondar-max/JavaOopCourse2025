package ru.java.bondarmax.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Создаем список людей
        List<Person> persons = Arrays.asList(
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

        persons.forEach(System.out::println);

        // А) Получить список уникальных имен
        List<String> uniqueNames = persons.stream()
                .map(Person::name)
                .distinct()
                .toList();

        System.out.println("А) Уникальные имена: " + uniqueNames);

        // Б) Вывести список уникальных имен в формате: Имена: Иван, Сергей, Петр.
        String formattedNames = persons.stream()
                .map(Person::name)
                .distinct()
                .collect(Collectors.joining(", ", "Имена: ", "."));

        System.out.println("Б) " + formattedNames);

        // В) Получить список людей младше 18, посчитать для них средний возраст
        List<Person> personsUnder18 = persons.stream()
                .filter(age -> age.age() < 18)
                .toList();

        OptionalDouble averageAgeUnder18 = persons.stream()
                .filter(p -> p.age() < 18)
                .mapToInt(Person::age)
                .average();

        System.out.println("В) Люди младше 18: " + personsUnder18);

        if (averageAgeUnder18.isPresent()) {
            System.out.println("   Средний возраст: " + averageAgeUnder18);
        } else {
            System.out.println("   Нет людей младше 18");
        }

        // Г) Получить Map: ключи – имена, значения – средний возраст
        Map<String, Double> averageAgesByName = persons.stream()
                .collect(Collectors.groupingBy(
                        Person::name,
                        Collectors.averagingInt(Person::age)
                ));

        System.out.println("Г) Map (имя -> средний возраст):");
        averageAgesByName.forEach((name, averageAge) ->
                System.out.println("      " + name + " -> " + averageAge + " лет"));

        // Д) Получить людей от 20 до 45, вывести имена в порядке убывания возраста
        List<Person> personsBetween20And45 = persons.stream()
                .filter(p -> p.age() >= 20 && p.age() <= 45)
                .sorted((p1, p2) -> p2.age() - p1.age())
                .toList();

        System.out.println("Д) Люди от 20 до 45 лет (по убыванию возраста):");
        personsBetween20And45.forEach(p -> System.out.println("   " + p.name() + " - " + p.age() + " лет"));
    }
}
