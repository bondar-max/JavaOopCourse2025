package ru.java.bondarmax.temperature_model;

// Модель
public class TemperatureModel {
    public double convertTemperature(double value, String sourceScaleLabel, String targetScaleLabel) {
        // Сначала преобразуем в Цельсии
        double celsius = switch (sourceScaleLabel) {
            case "Фаренгейт" -> (value - 32) * 5 / 9;
            case "Кельвин" -> value - 273.15;
            default -> value;
        };

        // Затем преобразуем из Цельсия в целевую шкалу
        return switch (targetScaleLabel) {
            case "Фаренгейт" -> celsius * 9 / 5 + 32;
            case "Кельвин" -> celsius + 273.15;
            default -> celsius;
        };
    }
}

