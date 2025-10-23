package ru.java.bondarmax.lambda_main;

import ru.java.bondarmax.lambda_person.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
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

        // А) Получить список уникальных имен
        List<String> uniqueNames = people.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        System.out.println("А) Уникальные имена: " + uniqueNames);

        // Б) Вывести список уникальных имен в формате: Имена: Иван, Сергей, Петр.
        String formattedNames = people.stream()
                .map(Person::getName)
                .distinct()
                .collect(Collectors.joining(", ", "Имена: ", "."));

        System.out.println("Б) " + formattedNames);

        // В) Получить список людей младше 18, посчитать для них средний возраст
        List<Person> under18 = people.stream()
                .filter(age -> age.getAge() < 18)
                .toList();

        OptionalDouble averageAgeUnder18 = people.stream()
                .filter(p -> p.getAge() < 18)
                .mapToInt(Person::getAge)
                .average();

        System.out.println("В) Люди младше 18: " + under18);

        if (averageAgeUnder18.isPresent()) {
            System.out.println("   Средний возраст: " + averageAgeUnder18);
        } else {
            System.out.println("   Нет людей младше 18");
        }

        // Г) Получить Map: ключи – имена, значения – средний возраст
        Map<String, Double> nameToAverageAge = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getName,
                        Collectors.averagingDouble(Person::getAge)
                ));

        System.out.println("Г) Map (имя -> средний возраст):");
        nameToAverageAge.forEach((name, avgAge) ->
                System.out.println("      " + name + " -> " + avgAge + " лет"));

        // Д) Получить людей от 20 до 45, вывести имена в порядке убывания возраста
        List<Person> age20to45 = people.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .toList();

        System.out.println("Д) Люди от 20 до 45 лет (по убыванию возраста):");
        age20to45.forEach(p -> System.out.println("   " + p.getName() + " - " + p.getAge() + " лет"));
    }
}
