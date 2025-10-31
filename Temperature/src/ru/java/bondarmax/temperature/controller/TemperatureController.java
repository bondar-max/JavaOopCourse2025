package ru.java.bondarmax.temperature.controller;

import ru.java.bondarmax.temperature.model.TemperatureModelInterface;
import ru.java.bondarmax.temperature.scales.TemperatureScaleInterface;
import ru.java.bondarmax.temperature.view.TemperatureViewInterface;

// Контроллер
public class TemperatureController {
    private final TemperatureModelInterface model;
    private final TemperatureViewInterface view;

    public TemperatureController(TemperatureModelInterface model, TemperatureViewInterface view) {
        this.model = model;
        this.view = view;

        // Устанавливаем шкалы в view
        view.setTemperatureScales(model.getAvailableScales());

        // Регистрация обработчиков событий
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Обработчик для кнопки конвертации
        view.setConvertButtonListener(e -> convertTemperature());
        // Обработка нажатия Enter в поле ввода
        view.setTemperatureFieldListener(e -> convertTemperature());
    }

    private void convertTemperature() {
        try {
            // Получение введенных данных
            Double inputValue = view.getInputTemperatureValue();

            if (inputValue == null) {
                view.setError("Введите значение температуры");
                return;
            }

            TemperatureScaleInterface fromScale = view.getSourceScale();
            TemperatureScaleInterface toScale = view.getTargetScale();

            double result = model.convertTemperature(inputValue, fromScale, toScale);

            view.setResult(result);
            view.setError("");
        } catch (NumberFormatException e) {
            view.setError("Ошибка: введите корректное число");
            view.clearResult();
        }
    }
}

